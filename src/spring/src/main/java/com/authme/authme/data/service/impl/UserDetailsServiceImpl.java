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
    private final ClassMapper classMapper;

    public UserDetailsServiceImpl(AuthMeUserRepository userRepository, ClassMapper classMapper) {
        this.userRepository = userRepository;
        this.classMapper = classMapper;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username)  {
        return classMapper
                .toUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!")));
    }
}
