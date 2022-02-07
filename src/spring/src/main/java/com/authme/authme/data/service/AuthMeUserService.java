package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;
import com.authme.authme.data.view.DataMonitorViewModel;

import java.util.Optional;

public interface AuthMeUserService {
    Optional<AuthMeUserEntity> findByUsername(String username);

    void registerAndLogin(RegisterServiceModel registerServiceModel);

    DataMonitorViewModel getDataMonitorViewModel();

    String generateGoldenToken();

    Boolean goldenTokenActive();

    String getCurrentUserGoldenToken();

    void init();

}
