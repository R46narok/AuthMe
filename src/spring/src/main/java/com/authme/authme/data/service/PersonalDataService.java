package com.authme.authme.data.service;

import com.authme.authme.data.binding.ManagerConsoleBindingModel;
import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.dto.FilePack;
import com.authme.authme.data.entity.AuthMeUserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PersonalDataService {
    Long newEntry();

    ProfileBindingModel getBindingModel();

    void patchProfile(ProfileBindingModel profileBindingModel);

    void deleteProfile(Long id);

    void uploadIdCardPictures(MultipartFile frontImage, MultipartFile backImage);

    Boolean checkDataValid(AuthMeUserEntity user, ValidateProfileBindingModel bindingModel);

    ManagerConsoleBindingModel getNewManagerConsoleBindingModel();

    FilePack getImage(String id) throws IOException;

    void updateBindingModel(ManagerConsoleBindingModel bindingModel);
}
