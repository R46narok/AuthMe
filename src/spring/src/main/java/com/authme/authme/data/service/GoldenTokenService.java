package com.authme.authme.data.service;

import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.view.GoldenTokenView;

import java.util.List;

public interface GoldenTokenService {
    String generateGoldenToken();

    boolean tokenBelongsToCurrentUser(String goldenToken);

    List<GoldenTokenView> getCurrentUserGoldenTokens();

    GoldenToken findById(String goldenToken);

    GoldenToken findByIdOrNull(String goldenToken);

    GoldenToken findByIdOrThrow(String goldenToken);

    void setTokenPermissions(String goldenToken, List<String> permissionsStrings);
}
