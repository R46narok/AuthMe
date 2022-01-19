package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.enums.AuthMeUserRole;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.RoleRepository;
import com.authme.authme.data.service.AuthMeUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthMeUserServiceImpl implements AuthMeUserService {
    private final AuthMeUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthMeUserServiceImpl(AuthMeUserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<AuthMeUserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void init() {
        AuthMeUserEntity user = new AuthMeUserEntity()
                .setUsername("test")
                .setPassword("8abb9bfc12232d7e0ca435b875173f577729923491d7e12a144fd26970b30060d7c8b04484e130e7")
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(0L);
        userRepository.saveAndFlush(user);
    }
}
