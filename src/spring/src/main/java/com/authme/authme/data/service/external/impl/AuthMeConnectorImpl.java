package com.authme.authme.data.service.external.impl;

import com.authme.authme.data.service.external.AuthMeConnector;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthMeConnectorImpl implements AuthMeConnector {
    private final RestTemplate restTemplate;

    public AuthMeConnectorImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public <T> ResponseEntity<T> request(String url, HttpMethod method, HttpHeaders headers, Class<T> responseType, Map<String, ?> pathVariables) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, method, requestEntity, responseType, pathVariables == null ? new HashMap<>() : pathVariables);
    }
}
