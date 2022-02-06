package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;

public interface CurrentUserService {
    AuthMeUserEntity getCurrentLoggedUser();
}
