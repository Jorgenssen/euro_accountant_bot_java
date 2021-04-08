package com.euroaccountant.services;

import com.euroaccountant.utils.RequestResponseLoggingInterceptor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Lazy
@Component
public class ExchangeService {

    private RestTemplate restTemplate;

    @Autowired
    @Value("${bot.currenciesBaseURI}")
    private String currenciesBaseURI;

    public ExchangeService() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        this.restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
    }

    private JSONObject getRates(){
        return new JSONObject(restTemplate.getForObject(currenciesBaseURI, String.class)).getJSONObject("rates");
    }

    private JSONObject getBaseRates(String currency) {
        return new JSONObject(restTemplate.getForObject(currenciesBaseURI + "?base=" + currency, String.class)).getJSONObject("rates");
    }

    public List<String> getCurrenciesList() {
        List<String> currenciesList = new ArrayList<>();
        currenciesList.addAll(getRates().keySet());
        return currenciesList;
    }

    public double getCurrency(String currency) {
        return (Double) getRates().get(currency);
    }

    public double getBaseCurrency(String currency) {
        return (Double) getBaseRates(currency).get("EUR");
    }
}
