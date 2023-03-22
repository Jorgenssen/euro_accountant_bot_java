package com.euroaccountant.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LogManager.getLogger(this.getClass());

    @Nonnull
    @Override
    public ClientHttpResponse intercept(@NonNull HttpRequest request,
                                        @NonNull byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        if (log.isDebugEnabled()) {
            log.debug("===========================request begin================================================");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request body: {}", new String(body, StandardCharsets.UTF_8));
            log.debug("===========================request end==================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("===========================response begin===============================================");
            log.debug("Status code  : {}", response.getStatusCode());
            log.debug("Status text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.debug("===========================response end=================================================");
        }
    }
}
