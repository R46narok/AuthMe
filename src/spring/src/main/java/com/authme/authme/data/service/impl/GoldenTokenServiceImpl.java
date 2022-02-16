package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.service.CurrentUserService;
import com.authme.authme.data.service.GoldenTokenService;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.data.view.GoldenTokenView;
import com.authme.authme.exceptions.CommonErrorMessages;
import com.authme.authme.utils.ClassMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoldenTokenServiceImpl implements GoldenTokenService {
    private final Long expiryPeriod = 200L;

    private final GoldenTokenRepository goldenTokenRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionService permissionService;
    private final CurrentUserService currentUserService;
    private final ClassMapper classMapper;

    public GoldenTokenServiceImpl(GoldenTokenRepository goldenTokenRepository,
                                  AuthMeUserRepository userRepository, PermissionService permissionService, CurrentUserService currentUserService, ClassMapper classMapper) {
        this.goldenTokenRepository = goldenTokenRepository;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.currentUserService = currentUserService;
        this.classMapper = classMapper;
    }

    @Override
    public String generateGoldenToken() {
        AuthMeUserEntity user = currentUserService.getCurrentLoggedUser();
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
    public boolean tokenBelongsToCurrentUser(String goldenToken) {
        return currentUserService.getCurrentLoggedUser().getGoldenTokens().stream().anyMatch(t -> t.getId().equals(goldenToken));
    }

    @Override
    public List<GoldenTokenView> getCurrentUserGoldenTokens() {
        AuthMeUserEntity user = currentUserService.getCurrentLoggedUserOrNull();
        if (user == null)
            return new ArrayList<>();
        if (user.getGoldenTokens() != null && user.getGoldenTokens().stream().anyMatch(t -> t.getExpiry().isAfter(LocalDateTime.now()))) {
            return classMapper.toGoldenTokenViewList(
                    user.getGoldenTokens()
                            .stream()
                            .filter(t -> t.getExpiry().isAfter(LocalDateTime.now()))
                            .sorted(Comparator.comparing(GoldenToken::getExpiry))
                            .collect(Collectors.toList()),
                    permissionService.getAll());
        }
        return new ArrayList<>();
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
