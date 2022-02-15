package com.authme.authme.data.service.impl;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.CurrentUserService;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.utils.ClassMapper;
import com.authme.authme.utils.RemoteEndpoints;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
        return restTemplate.getForObject(RemoteEndpoints.entry(), Long.class);
    }

    @Override
    public ProfileBindingModel getBindingModel() {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        ProfileDTO profileDTO = restTemplate.getForObject(RemoteEndpoints.profile(user.getDataId()), ProfileDTO.class);
        return classMapper.toProfileBindingModel(profileDTO);
    }

    @Override
    public void patchProfile(ProfileBindingModel profileBindingModel) {
        AuthMeUserEntity user = currentUser.getCurrentLoggedUser();
        ProfileDTO profileDTO = classMapper.toProfileDTO(profileBindingModel);
        restTemplate.postForLocation(RemoteEndpoints.profile(user.getDataId()), profileDTO);
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

        return true;
    }
}
