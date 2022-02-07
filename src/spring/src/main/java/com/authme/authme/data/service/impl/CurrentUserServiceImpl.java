package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.service.CurrentUserService;
import com.authme.authme.exceptions.CommonErrorMessages;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    private final AuthMeUserRepository userRepository;

    public CurrentUserServiceImpl(AuthMeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthMeUserEntity getCurrentLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> CommonErrorMessages.username(username));
    }
}
