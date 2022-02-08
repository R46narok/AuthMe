package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;
import com.authme.authme.data.view.DataMonitorViewModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthMeUserService {
    Optional<AuthMeUserEntity> findByUsername(String username);

    void registerAndLogin(RegisterServiceModel registerServiceModel);

    DataMonitorViewModel getDataMonitorViewModel();

    String generateGoldenToken();

    Boolean goldenTokenActive();

    String getCurrentUserGoldenToken();

    AuthMeUserEntity getAssociatedUserByGoldenTokenOrNull(String goldenToken);

    String checkDataValidForUser(AuthMeUserEntity user, String requesterName, String remoteAddress, Map<String, String> data);

    void setTokenPermissions(List<String> permissionsStrings);

    void init();
}
