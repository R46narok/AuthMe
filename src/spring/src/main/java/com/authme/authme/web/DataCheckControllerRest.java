package com.authme.authme.web;

import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
public class DataCheckControllerRest {
    private final GoldenTokenService goldenTokenService;
    private final DataValidationRecordService dataValidationService;

    public DataCheckControllerRest(GoldenTokenService goldenTokenService, DataValidationRecordService dataValidationService) {
        this.goldenTokenService = goldenTokenService;
        this.dataValidationService = dataValidationService;
    }

    @Transactional
    @GetMapping("/identity/check/trigger")
    public ResponseEntity<String> firstVerificationRequest(@RequestHeader String goldenToken,
                                                           @RequestHeader String issuer,
                                                           HttpServletRequest request) {
        if(goldenTokenService.findByIdOrNull(goldenToken) == null)
            return ResponseEntity.notFound().build();
        if(goldenTokenService.findById(goldenToken).getExpiry().isBefore(LocalDateTime.now()))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dataValidationService.triggerDataValidationProcess(goldenToken, issuer, request.getRemoteAddr()));
    }

//    @Transactional
//    @GetMapping
//    public ResponseEntity<Boolean> secondVerificationRequest(@RequestHeader String goldenToken,
//                                                             @RequestHeader String platinumToken,
//                                                             @RequestBody )
}
