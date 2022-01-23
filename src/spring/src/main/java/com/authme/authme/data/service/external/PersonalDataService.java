package com.authme.authme.data.service.external;

import com.authme.authme.data.binding.ProfileBindingModel;
import org.springframework.stereotype.Service;

public interface PersonalDataService {
    Long newEntry();

    void getData(Long dataId);
}
