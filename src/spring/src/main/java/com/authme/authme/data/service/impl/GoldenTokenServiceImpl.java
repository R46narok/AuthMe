package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.CurrentUserService;
import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.service.GoldenTokenService;
import com.authme.authme.data.view.PermissionViewModel;
import com.authme.authme.exceptions.CommonErrorMessages;
import com.authme.authme.utils.ClassMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GoldenTokenServiceImpl implements GoldenTokenService {
    private final Long expiryPeriod = 200L;

    private final GoldenTokenRepository goldenTokenRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final CurrentUserService currentUserService;
    private final ClassMapper classMapper;
    private final DataValidationRecordService dataValidationRecordService;

    public GoldenTokenServiceImpl(GoldenTokenRepository goldenTokenRepository, AuthMeUserRepository userRepository, PermissionRepository permissionRepository, CurrentUserService currentUserService, ClassMapper classMapper, DataValidationRecordService dataValidationRecordService) {
        this.goldenTokenRepository = goldenTokenRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.currentUserService = currentUserService;
        this.classMapper = classMapper;
        this.dataValidationRecordService = dataValidationRecordService;
    }

    @Override
    public void deleteToken(String goldenToken) {
        if (goldenTokenRepository.existsById(goldenToken)) {
            goldenTokenRepository.deleteById(goldenToken);
        }
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
            if (!token.getPermissions().contains(new Permission().setFieldName(field)))
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
    public String triggerDataValidationProcess(String goldenToken, String issuer, String issuerIP) {
        GoldenToken token = goldenTokenRepository.findById(goldenToken)
                .orElseThrow(() -> CommonErrorMessages.token(goldenToken));
        if (token.getExpiry().isBefore(LocalDateTime.now()))
            return null;
        DataValidationRecord record = dataValidationRecordService.generateRecord(token.getUser(), issuer, issuerIP);
        token.setExpiry(LocalDateTime.now());
        goldenTokenRepository.saveAndFlush(token);
        return record.getPlatinumToken().substring(0, record.getPlatinumToken().length() / 2);
    }

    @Override
    public List<PermissionViewModel> getAllPermissionsTagged() {
        List<Permission> allPermissions = permissionRepository.findAll();
        AuthMeUserEntity currentUser = currentUserService.getCurrentLoggedUser();
        GoldenToken goldenToken = currentUser.getGoldenToken();
        if (goldenToken == null)
            return null;
        List<Permission> currentTokenPermissions = goldenToken.getPermissions();

        List<PermissionViewModel> viewModelList = classMapper.toPermissionViewModelList(allPermissions);

        for (PermissionViewModel model : viewModelList) {
            if (currentTokenPermissions.stream().anyMatch(p -> p.getId().equals(model.getId()))) {
                model.setAllowed(true);
            }
        }
        return viewModelList;
    }

}
