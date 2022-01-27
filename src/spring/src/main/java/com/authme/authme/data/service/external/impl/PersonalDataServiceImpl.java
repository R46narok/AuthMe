package com.authme.authme.data.service.external.impl;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.service.external.PersonalDataService;
import com.authme.authme.utils.ClassMapper;
import com.authme.authme.utils.RemoteEndpoints;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// TODO: Connect with ASP.NET secondary service
@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private final RestTemplate restTemplate;

    public PersonalDataServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Long newEntry() {
        return Long.valueOf(restTemplate.getForObject(RemoteEndpoints.entry(), String.class));
    }

    // TODO: change return type to a new DTO type
    @Override
    public ProfileDTO getData(Long dataId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("dataId", dataId.toString());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ProfileDTO> response =
                restTemplate.exchange(RemoteEndpoints.profile(), HttpMethod.GET, requestEntity, ProfileDTO.class);
        return response.getBody();
    }

    @Override
    public ProfileDTO patchData(Long dataId, ProfileDTO profileDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("dataId", dataId.toString());

        HttpEntity<ProfileDTO> requestEntity = new HttpEntity<>(profileDTO, headers);

        ResponseEntity<ProfileDTO> response = restTemplate.exchange(RemoteEndpoints.profile(), HttpMethod.POST, requestEntity, ProfileDTO.class);
        return response.getBody();
    }


}
