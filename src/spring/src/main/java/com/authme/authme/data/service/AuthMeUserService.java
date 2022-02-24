package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;

public interface AuthMeUserService {
    void registerAndLogin(RegisterServiceModel registerServiceModel);

    AuthMeUserEntity getRolesOrNull(String username);

    void setRole(String username, Integer role);

    void removeRole(String username, Integer role);

    void updatePassword(String newPassword);

    void init();
}
