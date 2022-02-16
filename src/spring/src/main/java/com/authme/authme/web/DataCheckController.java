package com.authme.authme.web;

import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.service.GoldenTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @ModelAttribute("bindingModel")
    public ValidateProfileBindingModel secondPartBindingModel() {
        return new ValidateProfileBindingModel();
    }

    @GetMapping("/identity/check")
    public String getFirstPage() {
        return "identity-check-first";
    }

    @GetMapping("/identity/check/validate")
    public String getSecondPage() {
        return "identity-check-second";
    }

    @GetMapping("/identity/check/result/data-valid")
    public String getDataValidPage() {
        return "data-valid";
    }

    @GetMapping("/identity/check/result/data-invalid")
    public String getDataInvalidPage() {
        return "data-invalid";
    }

    @Transactional
    @PostMapping("/identity/check")
    public String triggerProcess(@RequestParam String goldenToken,
                                 @RequestParam String issuerName,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        if(goldenTokenService.findByIdOrNull(goldenToken) == null)
            return "redirect:/no-such-golden-token";
        if(goldenTokenService.findById(goldenToken).getExpiry().isBefore(LocalDateTime.now()))
            return "redirect:/token-expired";
        String platinumToken = dataValidationService.triggerDataValidationProcess(goldenToken, issuerName, request.getRemoteAddr());
        redirectAttributes.addFlashAttribute("goldenToken", goldenToken);
        redirectAttributes.addFlashAttribute("platinumTokenLeft", platinumToken);
        return "redirect:/identity/check/validate";
    }

    @Transactional
    @PostMapping("/identity/check/validate")
    public String finishProcess(@RequestParam String goldenToken,
                                @RequestParam String platinumTokenLeft,
                                @RequestParam String platinumTokenRight,
                                ValidateProfileBindingModel bindingModel) {
        String status = dataValidationService.finishDataValidationProcessAndValidateData(goldenToken, platinumTokenLeft + platinumTokenRight, bindingModel);
        if(status.equals("no-permissions")) {
            return "redirect:/no-permission";
        }

        return "redirect:/identity/check/result/" + status;
    }
}
