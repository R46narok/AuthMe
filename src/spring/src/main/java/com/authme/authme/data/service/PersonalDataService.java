package com.authme.authme.data.service;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonalDataService {
    Long newEntry();

    ProfileDTO getData(Long dataId);

    ProfileDTO patchData(Long dataId, ProfileDTO toProfileDTO, List<MultipartFile> images);
}