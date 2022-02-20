package com.authme.authme.data.service;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.utils.Picture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface PersonalDataService {
    Long newEntry();

    ProfileBindingModel getBindingModel();

    void patchProfile(ProfileBindingModel profileBindingModel);

    void deleteProfile(Long id);

    void uploadIdCardPictures(MultipartFile frontImage, MultipartFile backImage);

    Boolean checkDataValid(AuthMeUserEntity user, Map<String, String> data);

    Boolean checkDataValid(AuthMeUserEntity user, ValidateProfileBindingModel bindingModel);
}
