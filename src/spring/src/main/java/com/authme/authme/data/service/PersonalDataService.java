package com.authme.authme.data.service;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.utils.Picture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface PersonalDataService {
    Long newEntry();

    ProfileBindingModel getBindingModel();

    void patchProfile(ProfileBindingModel profileBindingModel);
}
