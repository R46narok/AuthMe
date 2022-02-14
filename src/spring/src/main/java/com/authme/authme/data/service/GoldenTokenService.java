package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;

import java.util.List;
import java.util.Optional;

public interface GoldenTokenService {
    String generateFor(AuthMeUserEntity user);

    GoldenToken findById(String goldenToken);

    GoldenToken findByIdOrNull(String goldenToken);

    GoldenToken findByIdOrThrow(String goldenToken);

    void setTokenPermissions(String goldenToken, List<String> permissionsStrings);
}
