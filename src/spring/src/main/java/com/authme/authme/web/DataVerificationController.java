package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class DataVerificationController {
    private final AuthMeUserService userService;
    private final GoldenTokenService goldenTokenService;

    public DataVerificationController(AuthMeUserService userService, GoldenTokenService goldenTokenService) {
        this.userService = userService;
        this.goldenTokenService = goldenTokenService;
    }

    @Transactional
    @GetMapping("/identity/check/trigger")
    public ResponseEntity<String> firstVerificationRequest(@RequestHeader String goldenToken,
                                                           @RequestHeader String issuer,
                                                           HttpServletRequest request) {
        if(goldenTokenService.findById(goldenToken).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(goldenTokenService.triggerDataValidationProcess(goldenToken, issuer, request.getRemoteAddr()));
    }
}
