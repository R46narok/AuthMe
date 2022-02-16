package com.authme.authme.data.service;

import com.authme.authme.data.service.models.RegisterServiceModel;

public interface AuthMeUserService {
    void registerAndLogin(RegisterServiceModel registerServiceModel);

    void init();
}
