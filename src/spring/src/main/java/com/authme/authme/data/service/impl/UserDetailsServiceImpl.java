package com.authme.authme.data.service.impl;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.exceptions.CommonErrorMessages;
import com.authme.authme.utils.impl.ClassMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthMeUserService userService;

    public UserDetailsServiceImpl(AuthMeUserService userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username)  {
        return ClassMapper
                .toUserDetails(userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!")));
    }
}
