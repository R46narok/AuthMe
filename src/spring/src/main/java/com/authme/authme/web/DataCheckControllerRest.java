package com.authme.authme.web;

import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.dto.ValidatableResponse;
import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class DataCheckControllerRest {
    private final GoldenTokenService goldenTokenService;
    private final DataValidationRecordService dataValidationService;

    public DataCheckControllerRest(GoldenTokenService goldenTokenService, DataValidationRecordService dataValidationService) {
        this.goldenTokenService = goldenTokenService;
        this.dataValidationService = dataValidationService;
    }

    @Transactional
    @PostMapping("/api/identity/check")
    public ResponseEntity<Map<String, String>> triggerProcess(@RequestHeader String goldenToken,
                                                              @RequestHeader String issuerName,
                                                              HttpServletRequest request) {

        if (goldenTokenService.findByIdOrNull(goldenToken) == null)
            return ResponseEntity
                    .status(401)
                    .body(Map.of("status", "Invalid golden token!"));


        if (goldenTokenService.findById(goldenToken).getExpiry().isBefore(LocalDateTime.now()))
            return ResponseEntity
                    .status(401)
                    .body(Map.of("status", "Expired golden token!"));

        String platinumToken = dataValidationService.triggerDataValidationProcess(goldenToken, issuerName, request.getRemoteAddr());
        return ResponseEntity.ok(Map.of("status", "triggered", "result", platinumToken));
    }

    @Transactional
    @PostMapping("/api/identity/check/validate")
    public ResponseEntity<Map<String, String>> finishProcess(@RequestHeader String goldenToken,
                                                             @RequestHeader String platinumTokenLeft,
                                                             @RequestHeader String platinumTokenRight,
                                                             @RequestBody ValidateProfileBindingModel bindingModel) {
        String status =
                dataValidationService.finishDataValidationProcessAndValidateData(
                        goldenToken,
                        platinumTokenLeft + platinumTokenRight,
                        bindingModel);
        if (status.equals("invalid-golden-token"))
            return ResponseEntity.status(401).body(Map.of("status", "Invalid golden token!"));

        if (status.equals("no-permissions"))
            return ResponseEntity.status(401).body(Map.of("status", "No permissions for the requested fields!"));

        if (status.equals("invalid-platinum-token"))
            return ResponseEntity.status(401).body(Map.of("status", "Invalid platinum token!"));

        return ResponseEntity.ok().body(Map.of("status", "finished", "result", status));
    }
}
