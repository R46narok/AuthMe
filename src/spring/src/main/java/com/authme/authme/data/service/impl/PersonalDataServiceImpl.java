package com.authme.authme.data.service.impl;

import com.authme.authme.config.CustomConfig;
import com.authme.authme.data.binding.ManagerConsoleBindingModel;
import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.dto.FilePack;
import com.authme.authme.data.dto.ProfileDTOGet;
import com.authme.authme.data.dto.ProfileDTOPost;
import com.authme.authme.data.dto.ValidatableResponse;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.CurrentUserService;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.exceptions.CommonErrorMessages;
import com.authme.authme.utils.ClassMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
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
        ValidatableResponse<Long> response = request(customConfig.getDotnetEndpoint() + "/api/identity", HttpMethod.GET, null, null,
                new ParameterizedTypeReference<>() {
                });
        return response.getResult();
    }

    public ProfileDTOGet getProfile(Long dataId) {
        ValidatableResponse<ProfileDTOGet> response =
                request(customConfig.getDotnetEndpoint() + "/api/identity/" + dataId,
                        HttpMethod.GET,
                        null,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        if (response == null || !response.isValid()) {
            throw CommonErrorMessages.errorExtractingEntityFromSecondService();
        }
        return response.getResult();
    }

    @Override
    public ProfileBindingModel getBindingModel() {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        return classMapper.toProfileBindingModel(getProfile(user.getDataId()));
    }

    @Override
    public void patchProfile(ProfileBindingModel profileBindingModel) {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        ProfileDTOPost profileDTOPost = classMapper.toProfileDTOSend(profileBindingModel);
        profileDTOPost.setId(user.getId());
        request(customConfig.getDotnetEndpoint() + "/api/identity/" + user.getDataId(),
                HttpMethod.POST,
                null,
                profileDTOPost,
                new ParameterizedTypeReference<Void>() {
                });
    }

    @Override
    public void deleteProfile(Long id) {
        request(customConfig.getDotnetEndpoint() + "/api/identity/" + id, HttpMethod.DELETE, null, null, new ParameterizedTypeReference<Void>() {
        });
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

        request(customConfig.getDotnetEndpoint() + "/api/identityvalidity", HttpMethod.POST, headers, body, new ParameterizedTypeReference<Void>() {
        });
    }

    @Override
    public Boolean checkDataValid(AuthMeUserEntity user, ValidateProfileBindingModel bindingModel) {
        ProfileDTOGet profile = getProfile(user.getDataId());
        if (!bindingModel.getName().equals("") &&
                (!profile.getName().getValidated() ||
                !bindingModel.getName().equals(profile.getName().getValue())))
            return false;

        if (!bindingModel.getMiddleName().equals("") &&
                (!profile.getMiddleName().getValidated() ||
                !bindingModel.getMiddleName().equals(profile.getMiddleName().getValue())))
            return false;
        if (!bindingModel.getSurname().equals("") &&
                (!profile.getSurname().getValidated() ||
                !bindingModel.getSurname().equals(profile.getSurname().getValue())))
            return false;
        if (bindingModel.getDateOfBirth() != null &&
                (!profile.getDateOfBirth().getValidated() ||
                !bindingModel.getDateOfBirth().equals(profile.getDateOfBirth().getValue())))
            return false;
        return true;
    }

    @Override
    public ManagerConsoleBindingModel getNewManagerConsoleBindingModel() {
//      request(customConfig.getDotnetEndpoint() + "/api/manager",
        return request("http://localhost:8080/test/bindingModel",
                        HttpMethod.GET, null, null,
                        new ParameterizedTypeReference<>() {});
    }

    @Override
    public FilePack getImage(String id) throws IOException {
        ResponseEntity<byte[]> response = requestResponse("http://localhost:8080/test/image/" + id,
                HttpMethod.GET, null, null, new ParameterizedTypeReference<>() {
        });

        String contentType = response.getHeaders().get("Content-Type").stream().findFirst().orElse(null);

        File temp = File.createTempFile("xtra------------", "------------xtra");
        temp.deleteOnExit();
        OutputStream os = new FileOutputStream(temp);
        os.write(response.getBody());
        os.close();
        return new FilePack().setTemp(temp).setMimeType(contentType);
    }

    @Override
    public void updateBindingModel(ManagerConsoleBindingModel bindingModel) {
        request("http://localhost:8080/test/bindingModel",
                HttpMethod.POST, null, bindingModel,
                new ParameterizedTypeReference<>() {
                });
    }

    private <T, B> T request(String endpoint, HttpMethod method, HttpHeaders headers, B body, ParameterizedTypeReference<T> typeReference) {
        return requestResponse(endpoint, method, headers, body, typeReference).getBody();
    }

    private <T, B> ResponseEntity<T> requestResponse(String endpoint, HttpMethod method, HttpHeaders headers, B body, ParameterizedTypeReference<T> typeReference) {
        try {
            if (headers == null)
                headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<B> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<T> response =
                    restTemplate.exchange(endpoint, method, requestEntity, typeReference);

            return response;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                retrieveToken();
                return requestResponse(endpoint, method, headers, body, typeReference);
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
