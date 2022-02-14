package com.authme.authme.web;

import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Controller
public class DataCheckController {
    private final DataValidationRecordService dataValidationService;
    private final GoldenTokenService goldenTokenService;

    public DataCheckController(DataValidationRecordService dataValidationService, GoldenTokenService goldenTokenService) {
        this.dataValidationService = dataValidationService;
        this.goldenTokenService = goldenTokenService;
    }

    @GetMapping("/identity/check")
    public String getFirstPage() {
        return "identity-check-first";
    }

    @GetMapping("/identity/check/validate")
    public String getSecondPage() {
        return "identity-check-second";
    }

    @Transactional
    @PostMapping("/identity/check")
    public String triggerProcess(@RequestParam String goldenToken,
                                 @RequestParam String issuerName,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        if(goldenTokenService.findByIdOrNull(goldenToken) == null)
            return "redirect:/404";
        if(goldenTokenService.findById(goldenToken).getExpiry().isBefore(LocalDateTime.now()))
            return "redirect:/404";
        String platinumToken = dataValidationService.triggerDataValidationProcess(goldenToken, issuerName, request.getRemoteAddr());
        redirectAttributes.addFlashAttribute("goldenToken", goldenToken);
        redirectAttributes.addFlashAttribute("platinumTokenLeft", platinumToken);
        return "redirect:/identity/check/validate";
    }
}
