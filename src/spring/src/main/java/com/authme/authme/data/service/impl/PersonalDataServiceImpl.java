package com.authme.authme.data.service.impl;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.utils.RemoteEndpoints;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public ProfileDTO patchData(Long dataId, ProfileDTO profileDTO, List<MultipartFile> pictures) {
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("body", profileDTO);

        for (MultipartFile picture : pictures) {
                body.add("pictures", picture.getResource());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("dataId", dataId.toString());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        ResponseEntity<ProfileDTO> response =
                restTemplate.exchange(
                        RemoteEndpoints.profile(),
                        HttpMethod.POST,
                        requestEntity,
                        ProfileDTO.class);
        return response.getBody();
    }


}
