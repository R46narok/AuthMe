package com.authme.authme.data.service;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.entity.AuthMeUserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PersonalDataService {
    Long newEntry();

    ProfileBindingModel getBindingModel();

    void patchProfile(ProfileBindingModel profileBindingModel);

    void deleteProfile(Long id);

    void uploadIdCardPictures(MultipartFile frontImage, MultipartFile backImage);

    Boolean checkDataValid(AuthMeUserEntity user, ValidateProfileBindingModel bindingModel);
}
