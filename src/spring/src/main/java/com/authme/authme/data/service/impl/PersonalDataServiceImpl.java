package com.authme.authme.data.service.impl;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.dto.ValidatableResponse;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.CurrentUserService;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.exceptions.CommonErrorMessages;
import com.authme.authme.utils.ClassMapper;
import com.authme.authme.utils.RemoteEndpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private static final String clientSecret = "WD57Q~88~v2Vta21TM.EqsJygrPWcPG6nbZwX";
    private static final String clientId = "c4a2ab21-acdb-4a07-83be-b07b28fffe54";
    private static final String resource = "api://bc005232-4de2-4e23-a624-17f0921944de";
    private static final String tenant = "505fc22a-c476-49ef-97df-da53b5b7dfe9";
    private static final String instanceId = "https://login.microsoftonline.com/";
    private static String accessToken = "";

    private final RestTemplate restTemplate;
    private final ClassMapper classMapper;
    private final CurrentUserService currentUser;

    public PersonalDataServiceImpl(RestTemplate restTemplate, ClassMapper classMapper, CurrentUserService currentUser) {
        this.restTemplate = restTemplate;
        this.classMapper = classMapper;
        this.currentUser = currentUser;
    }

    @Override
    public Long newEntry() {
        ValidatableResponse<Long> response =
                restTemplate.exchange(RemoteEndpoints.entry(), HttpMethod.GET, null,
                                new ParameterizedTypeReference<ValidatableResponse<Long>>() {
                                })
                        .getBody();
        if (response == null || !response.isValid()) {
            throw CommonErrorMessages.errorCreatingEntityInSecondService();
        }
        return response.getResult();
    }

    @Override
    public ProfileBindingModel getBindingModel() {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        ValidatableResponse<ProfileDTO> response =
                restTemplate.exchange(RemoteEndpoints.profile(user.getDataId()), HttpMethod.GET, null,
                                new ParameterizedTypeReference<ValidatableResponse<ProfileDTO>>() {
                                })
                        .getBody();
        if (response == null || !response.isValid()) {
            throw CommonErrorMessages.errorExtractingEntityFromSecondService();
        }
        return classMapper.toProfileBindingModel(response.getResult());
    }

    @Override
    public void patchProfile(ProfileBindingModel profileBindingModel) {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();

        ProfileDTO profileDTO = classMapper.toProfileDTO(profileBindingModel);
        profileDTO.setId(user.getId());
        HttpEntity<ProfileDTO> entity = new HttpEntity<>(profileDTO);

        restTemplate.exchange(RemoteEndpoints.profile(user.getDataId()), HttpMethod.POST, entity, Void.class);
    }

    @Override
    public void deleteProfile(Long id) {
        restTemplate.delete(RemoteEndpoints.profile(id));
    }


    @Override
    public void uploadIdCardPictures(MultipartFile frontImage, MultipartFile backImage) {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();
        body.add("id", user.getDataId());
        body.add("documentFront", frontImage.getResource());
        body.add("documentBack", backImage.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        restTemplate.exchange(RemoteEndpoints.picture(), HttpMethod.POST, requestEntity, Void.class);
    }

    @Override
    public Boolean checkDataValid(AuthMeUserEntity user, Map<String, String> data) {

        return true;
    }

    @Override
    public Boolean checkDataValid(AuthMeUserEntity user, ValidateProfileBindingModel bindingModel) {

        return false;
    }

    private void retrieveToken() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("grant_type", "client_credentials");
        headers.set("client_id", clientId);
        headers.set("client_secret", clientSecret);
        headers.set("resource", resource);
        String json =
                restTemplate.exchange(instanceId + tenant + "/oauth2/token",
                                HttpMethod.GET,
                                null,
                                String.class)
                        .getBody();
        JsonNode node = new ObjectMapper().readTree(json);
        accessToken = node.get("access_token").asText();
    }
}
