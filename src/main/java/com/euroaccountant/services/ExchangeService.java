package com.euroaccountant.services;

import com.euroaccountant.utils.RequestResponseLoggingInterceptor;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Lazy
@Component
public class ExchangeService {

    private final RestTemplate restTemplate;

    @Value("${bot.currenciesBaseURI}")
    private String currenciesBaseURI;

    public ExchangeService() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        this.restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
    }

    private UriComponentsBuilder preBuildURI() {
        return UriComponentsBuilder.fromUriString(currenciesBaseURI);
    }

    private JSONObject getRates(){
        return new JSONObject(restTemplate.getForObject(preBuildURI().build().toUri(), String.class)).getJSONObject("rates");
    }

    private JSONObject getBaseRates(String currency) {
        URI uri = preBuildURI().queryParam("base", currency).build().toUri();
        return new JSONObject(restTemplate.getForObject(uri, String.class)).getJSONObject("rates");
    }

    public List<String> getCurrenciesList() {
        return new ArrayList<>(getRates().keySet());
    }

    public double getCurrency(String currency) {
        return (Double) getRates().get(currency);
    }

    public double getBaseCurrency(String currency) {
        return (Double) getBaseRates(currency).get("EUR");
    }
}
