package com.authme.authme.data.service.impl;

import com.authme.authme.config.CustomConfig;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private static String accessToken = "";

    private final RestTemplate restTemplate;
    private final ClassMapper classMapper;
    private final CurrentUserService currentUser;
    private final CustomConfig customConfig;

    public PersonalDataServiceImpl(RestTemplate restTemplate, ClassMapper classMapper, CurrentUserService currentUser, CustomConfig customConfig) {
        this.restTemplate = restTemplate;
        this.classMapper = classMapper;
        this.currentUser = currentUser;
        this.customConfig = customConfig;
    }

    @Override
    public Long newEntry() {
        ValidatableResponse<Long> response = request(RemoteEndpoints.entry(), HttpMethod.GET, null, null,
                new ParameterizedTypeReference<ValidatableResponse<Long>>());
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

    private <T, B> T request(String endpoint, HttpMethod method, HttpHeaders headers, B body, ResponseEntity<T> responseEntity) {
        try {
            if (headers == null)
                headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<B> requestEntity = new HttpEntity<B>(body, headers);

            ResponseEntity<T> response =
                    restTemplate.exchange(endpoint, method, requestEntity,
                            new ParameterizedTypeReference<>() {
                            });

            T entity = (T) response.getBody();
            if (entity == null) {
                throw CommonErrorMessages.errorCreatingEntityInSecondService();
            }
            return entity;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().value() == 401){
                retrieveToken();
                return request(endpoint, method, headers, body);
            }
            throw e;
        }
    }

    private void retrieveToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", customConfig.getAzureClientId());
        body.add("client_secret", customConfig.getAzureClientSecret());
        body.add("resource", customConfig.getAzureResource());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        String json =
                restTemplate.exchange(customConfig.getAzureInstance() +
                                        customConfig.getAzureTenant() +
                                        "/oauth2/token",
                                HttpMethod.POST,
                                requestEntity,
                                String.class)
                        .getBody();
        JsonNode node = null;
        try {
            node = new ObjectMapper().readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        accessToken = node.get("access_token").asText();
    }
}
