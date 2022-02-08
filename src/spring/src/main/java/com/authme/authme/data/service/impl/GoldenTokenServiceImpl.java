package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.GoldenTokenService;
import com.authme.authme.exceptions.CommonErrorMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GoldenTokenServiceImpl implements GoldenTokenService {
    private final Long expiryPeriod = 200L;

    private final GoldenTokenRepository goldenTokenRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public GoldenTokenServiceImpl(GoldenTokenRepository goldenTokenRepository, AuthMeUserRepository userRepository, PermissionRepository permissionRepository) {
        this.goldenTokenRepository = goldenTokenRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void deleteToken(String goldenToken) {
        if(goldenTokenRepository.existsById(goldenToken)){
            goldenTokenRepository.deleteById(goldenToken);
        }
    }

    @Override
    public String generateFor(AuthMeUserEntity user) {
        GoldenToken newToken =
                new GoldenToken()
                        .setId(UUID.randomUUID().toString())
                        .setExpiry(LocalDateTime.now().plusMinutes(expiryPeriod))
                        .setUser(user);
        newToken = goldenTokenRepository.saveAndFlush(newToken);
        user.setGoldenToken(newToken);
        userRepository.saveAndFlush(user);
        return newToken.getId();
    }

    @Override
    public Optional<GoldenToken> findById(String goldenToken) {
        return goldenTokenRepository.findById(goldenToken);
    }

    @Override
    public boolean hasPermission(Map<String, String> data, String tokenId) {
        GoldenToken token = goldenTokenRepository.findById(tokenId).orElseThrow(() -> CommonErrorMessages.token(tokenId));
        for (String field : data.keySet()) {
            if(!token.getPermissions().contains(new Permission().setFieldName(field)))
                return false;
        }
        return true;
    }

    @Override
    public void setPermissionsForToken(GoldenToken goldenToken, List<String> permissionsStrings) {
        List<Permission> permissions = new ArrayList<>();

        for (String permissionString : permissionsStrings) {
            Long permissionId = Long.parseLong(permissionString);
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> CommonErrorMessages.permission(permissionId));
            permissions.add(permission);
        }

        goldenToken.setPermissions(permissions);
        goldenTokenRepository.saveAndFlush(goldenToken);
    }

    @Override
    public ResponseEntity<String> triggerDataValidationProcess(String goldenToken) {
        GoldenToken token = goldenTokenRepository.findById(goldenToken)
                .orElseThrow(() -> CommonErrorMessages.token(goldenToken));

        return null;
    }

}
