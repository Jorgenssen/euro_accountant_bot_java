package com.euroaccountant;

import com.euroaccountant.helpers.NumericHelper;
import com.euroaccountant.helpers.RoundHelper;
import com.euroaccountant.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;

@Component
public class EuroAccountantBot extends TelegramLongPollingBot {

    private static final Logger log = LogManager.getLogger(EuroAccountantBot.class);

    private final String botUsername;
    private final String token;

    private String currency;

    @Lazy
    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    public EuroAccountantBot(@Value("${bot.botUsername}") String botUsername, @Value("${bot.token}") String token) {
        this.botUsername = botUsername;
        this.token = token;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String addressee = update.getMessage()
                    .getChatId() + " (" +
                    update.getMessage().getFrom().getFirstName() + " " +
                    update.getMessage().getFrom().getLastName() + ")";
            String command = update.getMessage().getText();

            log.debug("Message: \"" + command + "\" has been received from " + addressee);

            if (command.equals("/help") || command.equals("/help@EuroAccountant_bot")) {
                sendMessage(createHelpMessage(update.getMessage().getChatId()), addressee);
            } else if (command.equals("/mycurrency") || command.equals("/mycurrency@EuroAccountant_bot")) {
                sendMessage(myCurrencyMessage(update.getMessage().getChatId()), addressee);
                if (!isCurrency())
                    sendMessage(selectCurrencyMessage(update.getMessage().getChatId()), addressee);

            } else if (command.equals("/currencies") || command.equals("/currencies@EuroAccountant_bot")) {
                sendMessage(selectCurrencyMessage(update.getMessage().getChatId()), addressee);

            } else if (isCommand(command) && ifCurrencyInList(Arrays.asList(command.split(" ")).get(1).toUpperCase())){
                setCurrency(Arrays.asList(command.split(" ")).get(1));
                sendMessage(myCurrencyMessage(update.getMessage().getChatId()), addressee);

            } else if(isCommand(command) && isCurrency() &&
                    NumericHelper.isNumeric(Arrays.asList(command.split(" ")).get(1)))  {
                if (Arrays.asList(command.split(" ")).size() > 2) {
                    if (ifCurrencyInList(Arrays.asList(command.split(" ")).get(2).toUpperCase())) {
                        sendMessage(currencyToEuroMessage(update.getMessage().getChatId(),
                                Arrays.asList(command.split(" ")).get(1),
                                Arrays.asList(command.split(" ")).get(2).toUpperCase()),
                                addressee);
                    }
                } else if (isCommand(command) && Arrays.asList(command.split(" ")).size() == 2) {
                        sendMessage(euroToCurrencyMessage(update.getMessage().getChatId(),
                                Arrays.asList(command.split(" ")).get(1),
                                currency),
                                addressee);
                }
            }
        }
    }

    private void sendMessage(SendMessage createMessageSupplier, String addressee) {
        try {
            execute(createMessageSupplier);
            log.debug("Message: \"" + createMessageSupplier.getText() + "\" has been sent to " + addressee);
        } catch (TelegramApiException e) {
            log.error("Failed to send message to {} due to error: {}", addressee, e.getMessage());
        }
    }

    private SendMessage createHelpMessage(long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Hello, I'm your EuroAccountant, I was born to calculate currencies to Euro and back." +
                        "\n\nAll you need is to set your native currency then use simple commands." +
                        "\n\nFor example:" +
                        "\n\"/currencies\" - I will show you a list of currencies I support" +
                        "\n\"/accountant HUF\" - I will set Hungarian forint as your native currency" +
                        "\n\"/accountant  10 HUF\" - I will count 10 Hungarian forintes to Euro" +
                        "\n\"/accountant  10\" - I will count 10 Euro to Hungarian forintes");

    }

    private boolean isCurrency() {
        return this.currency != null;
    }

    private boolean isCommand(String command) {
        return command.contains("/accountant") || command.contains("/accountant@EuroAccountant_bot");
    }

    private void setCurrency(String currency) {
        this.currency = currency.toUpperCase();
    }

    private boolean ifCurrencyInList(String currency) {
        return exchangeService.getCurrenciesList().stream().anyMatch(str -> str.trim().equals(currency));
    }

    private SendMessage myCurrencyMessage(long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Selected currency is " + currency);
    }

    private SendMessage selectCurrencyMessage(long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Select your currency from the list:\n" + exchangeService.getCurrenciesList().toString());
    }

    private SendMessage currencyToEuroMessage(long chatId, String amount, String currency) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(amount + " " + currency + " is " + calcCurrencyToEuro(amount, currency) + " EUR");
    }

    private SendMessage euroToCurrencyMessage(long chatId, String amount, String currency) {
        return new SendMessage()
                .setChatId(chatId)
                .setText(amount + " EUR is " + calcEuroToCurrency(amount, currency) + " " + currency);
    }

    private double calcCurrencyToEuro(String amount, String currency){
        double a = Double.parseDouble(amount);
        return RoundHelper.round(a*exchangeService.getBaseCurrency(currency),2);
    }

    private double calcEuroToCurrency(String amount, String currency) {
        double a = Double.parseDouble(amount);
        return RoundHelper.round(a*exchangeService.getCurrency(currency),2);
    }

}