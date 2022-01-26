package com.authme.authme.data.service.external;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import org.springframework.stereotype.Service;

public interface PersonalDataService {
    Long newEntry();

    ProfileDTO getData(Long dataId);

    void patchData(Long dataId, ProfileDTO toProfileDTO);
}
