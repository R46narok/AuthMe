package com.authme.authme.web;

import com.authme.authme.data.dto.DataVerificationDTO;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/check")
    @Transactional
    public ResponseEntity<String> checkDataIsValid(@RequestBody DataVerificationDTO body,
                                                   @RequestHeader("name") String requesterName,
                                                   HttpServletRequest request) {
        AuthMeUserEntity user = userService.getAssociatedUserByGoldenTokenOrNull(body.getGoldenToken());
        if (user == null)
            return ResponseEntity.notFound().build();
        if(!goldenTokenService.hasPermission(body.getData(), body.getGoldenToken())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String platinumToken = userService.checkDataValidForUser(user, requesterName, request.getRemoteAddr(), body.getData());
        if(platinumToken == null)
            return ResponseEntity.ok("{\"valid\":false}");
        return ResponseEntity.ok("{\"valid\":true}");
    }

    @GetMapping("/data/trigger")
    public ResponseEntity<String> firstVerificationRequest(@RequestHeader String goldenToken) {
        if(goldenTokenService.findById(goldenToken).isEmpty())
            return ResponseEntity.notFound().build();
        return goldenTokenService.triggerDataValidationProcess(goldenToken);
    }
}
