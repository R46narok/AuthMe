package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.service.GoldenTokenService;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.exceptions.CommonErrorMessages;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GoldenTokenServiceImpl implements GoldenTokenService {
    private final Long expiryPeriod = 200L;

    private final GoldenTokenRepository goldenTokenRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionService permissionService;

    public GoldenTokenServiceImpl(GoldenTokenRepository goldenTokenRepository,
                                  AuthMeUserRepository userRepository, PermissionService permissionService) {
        this.goldenTokenRepository = goldenTokenRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    //TODO: There's a bug where if you generate a token the
    // already started validation process becomes unable to finish
    // because the new token overwrites the previous
    @Override
    public String generateFor(AuthMeUserEntity user) {
        GoldenToken newToken =
                new GoldenToken()
                        .setId(UUID.randomUUID().toString())
                        .setExpiry(LocalDateTime.now().plusMinutes(expiryPeriod))
                        .setUser(user);
        newToken = goldenTokenRepository.saveAndFlush(newToken);
        user.getGoldenTokens().add(newToken);
        userRepository.saveAndFlush(user);
        return newToken.getId();
    }

    @Override
    public GoldenToken findById(String goldenToken) {
        return goldenTokenRepository.findById(goldenToken).orElseThrow(() -> CommonErrorMessages.token(goldenToken));
    }

    @Override
    public GoldenToken findByIdOrNull(String goldenToken) {
        return goldenTokenRepository.findById(goldenToken).orElse(null);
    }

    @Override
    public GoldenToken findByIdOrThrow(String goldenToken) {
        return goldenTokenRepository.findById(goldenToken).orElseThrow(() -> CommonErrorMessages.token(goldenToken));
    }

    @Override
    public void setTokenPermissions(String goldenToken, List<String> permissionsStrings) {
        GoldenToken token = findById(goldenToken);
        List<Permission> permissions = new ArrayList<>();
        for (String permissionsString : permissionsStrings) {
            permissions.add(permissionService.findById(Long.valueOf(permissionsString)));
        }
        token.setPermissions(permissions);
        goldenTokenRepository.saveAndFlush(token);
    }
}
