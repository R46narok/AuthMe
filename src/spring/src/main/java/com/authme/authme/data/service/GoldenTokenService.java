package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.view.PermissionViewModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GoldenTokenService {
    void deleteToken(String goldenToken);

    String generateFor(AuthMeUserEntity user);

    Optional<GoldenToken> findById(String goldenToken);

    boolean hasPermission(Map<String, String> data, String token);

    void setPermissionsForToken(GoldenToken goldenToken, List<String> permissionsStrings);

    String triggerDataValidationProcess(String goldenToken, String issuer, String issuerIP);

    List<PermissionViewModel> getAllPermissionsTagged();
}
