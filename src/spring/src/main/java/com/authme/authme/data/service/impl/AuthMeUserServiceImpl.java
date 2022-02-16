package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.enums.AuthMeUserRole;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.RoleRepository;
import com.authme.authme.data.service.*;
import com.authme.authme.data.service.models.RegisterServiceModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthMeUserServiceImpl implements AuthMeUserService {
    private final AuthMeUserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserDetailsService userDetailsService;
    private final PersonalDataService personalDataService;

    private final PasswordEncoder passwordEncoder;

    public AuthMeUserServiceImpl(AuthMeUserRepository userRepository,
                                 RoleRepository roleRepository,
                                 UserDetailsService userDetailsService,
                                 PersonalDataService personalDataService,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.personalDataService = personalDataService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerAndLogin(RegisterServiceModel registerServiceModel) {
        AuthMeUserEntity user = new AuthMeUserEntity()
                .setUsername(registerServiceModel.getUsername())
                .setPassword(passwordEncoder.encode(registerServiceModel.getPassword()))
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(personalDataService.newEntry());
        user = userRepository.saveAndFlush(user);

        UserDetails principal = this.userDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        user.getPassword(),
                        principal.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void init() {
        AuthMeUserEntity user = new AuthMeUserEntity()
                .setUsername("test")
                .setPassword("8abb9bfc12232d7e0ca435b875173f577729923491d7e12a144fd26970b30060d7c8b04484e130e7")
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(personalDataService.newEntry());
        AuthMeUserEntity mitko = new AuthMeUserEntity()
                .setUsername("mitko")
                .setPassword("addc1ad2644907e0422ccdb79538de69cd4daf72ab5e0a26e85ed8ceb5208b47101fb93f7520dcc4")
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(personalDataService.newEntry());

        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(mitko);
    }
}
