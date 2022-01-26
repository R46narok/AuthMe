package com.authme.authme.data.service.external;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthMeConnector {
    <T> ResponseEntity<T> request(String url, HttpMethod method, HttpHeaders headers, Class<T> responseType, Map<String, ?> pathVariables);
}
