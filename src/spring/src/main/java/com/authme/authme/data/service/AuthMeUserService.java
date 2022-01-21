package com.authme.authme.data.service;

import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;

import java.util.Optional;

public interface AuthMeUserService {
    Optional<AuthMeUserEntity> findByUsername(String username);

    void registerAndLogin(RegisterServiceModel registerServiceModel);

    void init();
}
