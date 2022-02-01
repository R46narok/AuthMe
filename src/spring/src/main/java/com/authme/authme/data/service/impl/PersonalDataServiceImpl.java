package com.authme.authme.data.service.impl;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.utils.RemoteEndpoints;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

// TODO: Connect with ASP.NET secondary service
@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private final RestTemplate restTemplate;

    public PersonalDataServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Long newEntry() {
        return restTemplate.getForObject(RemoteEndpoints.entry(), Long.class);
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
    public void patchData(Long dataId, ProfileDTO profileDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("dataId", dataId.toString());

        HttpEntity<ProfileDTO> requestEntity =
                new HttpEntity<>(profileDTO, headers);

        restTemplate.exchange(
                RemoteEndpoints.profile(),
                HttpMethod.POST,
                requestEntity,
                ProfileDTO.class);
    }

    @Override
    public List<String> getPictures(Long userId) {
        return Arrays.stream(restTemplate.getForObject(RemoteEndpoints.picture(userId), String[].class)).toList();
    }

    @Override
    public File getPicture(Long userId, Long pictureId) {
        MultipartFile response =
                restTemplate.getForEntity(RemoteEndpoints.picture(userId, pictureId), MultipartFile.class).getBody();
        try {
            File temp = File.createTempFile(userId + "-", "-" + pictureId);
            temp.deleteOnExit();

            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void uploadPictures(Long userId, List<File> pictures) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("userId", userId.toString());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        for (File picture : pictures) {
            body.add("pictures", picture);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(RemoteEndpoints.picture(userId), HttpMethod.POST, requestEntity, Void.class);
    }

    @Override
    public void deletePicture(Long userId, Long pictureId) {
        restTemplate.delete(RemoteEndpoints.picture(userId, pictureId));
    }
}
