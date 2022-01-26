package com.authme.authme.data.service.impl;

import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.utils.ClassMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthMeUserRepository userRepository;

    public UserDetailsServiceImpl(AuthMeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username)  {
        return ClassMapper
                .toUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!")));
    }
}
