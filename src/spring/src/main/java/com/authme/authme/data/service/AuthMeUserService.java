package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;

import java.util.Optional;

public interface AuthMeUserService {
    Optional<AuthMeUserEntity> findByUsername(String username);

    void init();
}
