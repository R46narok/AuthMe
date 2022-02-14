package com.authme.authme.data.service;

import com.authme.authme.data.service.models.RegisterServiceModel;
import com.authme.authme.data.view.DataMonitorViewModel;
import com.authme.authme.data.view.GoldenTokenView;

import java.util.List;

public interface AuthMeUserService {
    void registerAndLogin(RegisterServiceModel registerServiceModel);

    DataMonitorViewModel getDataMonitorViewModel();

    String generateGoldenToken();

    List<GoldenTokenView> getCurrentUserGoldenTokens();

    boolean tokenBelongsToCurrentUser(String goldenToken);

    void init();
}
