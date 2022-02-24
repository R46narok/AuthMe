package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.Role;
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
import java.util.stream.Collectors;

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
    public AuthMeUserEntity getRolesOrNull(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void setRole(String username, Integer role) {
        AuthMeUserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null)
            return;
        if(user.getRoles().stream().noneMatch(r -> r.getName().ordinal() == role))
            user.getRoles().add(roleRepository.findByName(AuthMeUserRole.values()[role]).get());
    }

    @Override
    public void removeRole(String username, Integer role) {
        AuthMeUserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null)
            return;
        if(user.getRoles().stream().anyMatch(r -> r.getName().ordinal() == role))
            user.setRoles(user.getRoles().stream().filter(r -> r.getName().ordinal() != role).collect(Collectors.toList()));
    }

    @Override
    public void init() {
        if (userRepository.count() == 0) {
            AuthMeUserEntity user = new AuthMeUserEntity()
                    .setUsername("test")
                    .setPassword("8abb9bfc12232d7e0ca435b875173f577729923491d7e12a144fd26970b30060d7c8b04484e130e7")
                    .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                    .setDataId(personalDataService.newEntry());

            userRepository.saveAndFlush(user);
        }
    }
}
