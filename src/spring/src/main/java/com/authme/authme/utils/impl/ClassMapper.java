package com.authme.authme.utils.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class ClassMapper {
    public static UserDetails toUserDetails(AuthMeUserEntity user) {
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
