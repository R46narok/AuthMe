package com.authme.authme.data.service.external.impl;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.service.external.PersonalDataService;
import com.authme.authme.data.service.external.AuthMeConnector;
import com.authme.authme.utils.RemoteEndpoints;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

// TODO: Connect with ASP.NET secondary service
@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private final RestTemplate restTemplate;
    private final AuthMeConnector connector;

    public PersonalDataServiceImpl(RestTemplateBuilder restTemplateBuilder, AuthMeConnector connector) {
        restTemplate = restTemplateBuilder.build();
        this.connector = connector;
    }

    @Override
    public Long newEntry() {
        return Long.valueOf(connector.request(RemoteEndpoints.entry(), HttpMethod.GET, null, String.class, null).getBody());
    }

    // TODO: change return type to a new DTO type
    @Override
    public ProfileDTO getData(Long dataId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("dataId", dataId.toString());
        ResponseEntity<ProfileDTO> response =
                connector.request(RemoteEndpoints.profile(), HttpMethod.GET, headers, ProfileDTO.class, null);
        return response.getBody();
    }

    @Override
    public void patchData(Long dataId, ProfileDTO profileDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("dataId", dataId.toString());

        HttpEntity<ProfileDTO> requestEntity = new HttpEntity<ProfileDTO>(profileDTO, headers);

        ResponseEntity<Void> response = restTemplate.exchange(RemoteEndpoints.profile(), HttpMethod.PATCH, requestEntity, Void.class);
        System.out.println(response.getStatusCode());
    }


}
