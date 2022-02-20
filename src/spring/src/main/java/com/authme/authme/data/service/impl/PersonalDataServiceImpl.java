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
}
