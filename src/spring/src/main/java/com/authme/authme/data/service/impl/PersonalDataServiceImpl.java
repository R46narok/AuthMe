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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
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
        ValidatableResponse<Integer> response = restTemplate.getForObject(RemoteEndpoints.entry(), ValidatableResponse.class);
        return Long.valueOf(response.getResult());
    }

    @Override
    public ProfileBindingModel getBindingModel() {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        ValidatableResponse<ProfileDTO> profileDTO =
                restTemplate.exchange(RemoteEndpoints.profile(user.getDataId()), HttpMethod.GET, null, new ParameterizedTypeReference<ValidatableResponse<ProfileDTO>>() {})
                        .getBody();
        return classMapper.toProfileBindingModel(profileDTO.getResult());
    }

    @Override
    public void patchProfile(ProfileBindingModel profileBindingModel) {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        ProfileDTO profileDTO = classMapper.toProfileDTO(profileBindingModel);

        HttpEntity<ProfileDTO> entity = new HttpEntity<>(profileDTO);
        restTemplate.exchange(RemoteEndpoints.profile(user.getDataId()), HttpMethod.POST, entity, Void.class);
    }

    @Override
    public void uploadIdCardPictures(MultipartFile frontImage, MultipartFile backImage) {

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
