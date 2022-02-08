package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.GoldenToken;
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

    ResponseEntity<String> triggerDataValidationProcess(String goldenToken);

}
