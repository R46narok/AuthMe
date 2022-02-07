package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;

public interface GoldenTokenService {
    void deleteToken(String goldenToken);

    String generateFor(AuthMeUserEntity user);
}
