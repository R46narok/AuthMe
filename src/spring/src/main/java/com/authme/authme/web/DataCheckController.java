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

    @ModelAttribute("invalidGoldenToken")
    public boolean invalidGoldenToken() {
        return false;
    }

    @ModelAttribute("tokenExpired")
    public boolean tokenExpired() {
        return false;
    }

    @ModelAttribute("goldenToken")
    public String goldenToken() {
        return "";
    }

    @ModelAttribute("issuerName")
    public String issuerName() {
        return "";
    }

    @GetMapping("/identity/check")
    public String getFirstPage() {
        return "identity-check-first";
    }

    @Transactional
    @PostMapping("/identity/check")
    public String triggerProcess(@RequestParam String goldenToken,
                                 @RequestParam String issuerName,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        if (goldenTokenService.findByIdOrNull(goldenToken) == null ||
                goldenTokenService.findById(goldenToken).getExpiry().isBefore(LocalDateTime.now()))
        {
            redirectAttributes.addFlashAttribute("goldenToken", goldenToken);
            redirectAttributes.addFlashAttribute("issuerName", issuerName);

            if(goldenTokenService.findByIdOrNull(goldenToken) == null)
                redirectAttributes.addFlashAttribute("invalidGoldenToken", true);
            else
                redirectAttributes.addFlashAttribute("tokenExpired", true);

            return "redirect:/identity/check";
        }
        String platinumToken = dataValidationService.triggerDataValidationProcess(goldenToken, issuerName, request.getRemoteAddr());
        redirectAttributes.addFlashAttribute("goldenToken", goldenToken);
        redirectAttributes.addFlashAttribute("platinumTokenLeft", platinumToken);
        return "redirect:/identity/check/validate";
    }

    //------------------------------------------------------------------------------------------------------------------

    @ModelAttribute("bindingModel")
    public ValidateProfileBindingModel secondPartBindingModel() {
        return new ValidateProfileBindingModel();
    }

    @ModelAttribute("invalidPlatinumToken")
    public boolean invalidPlatinumToken() {
        return false;
    }

    @ModelAttribute("noPermissions")
    public boolean noPermissions() {
        return false;
    }

    @ModelAttribute("platinumTokenLeft")
    public String platinumTokenLeft() {
        return "";
    }

    @ModelAttribute("platinumTokenRight")
    public String platinumTokenRight() {
        return "";
    }

    @GetMapping("/identity/check/validate")
    public String getSecondPage() {
        return "identity-check-second";
    }

    @Transactional
    @PostMapping("/identity/check/validate")
    public String finishProcess(@RequestParam String goldenToken,
                                @RequestParam String platinumTokenLeft,
                                @RequestParam String platinumTokenRight,
                                ValidateProfileBindingModel bindingModel,
                                RedirectAttributes redirectAttributes) {
        String status = dataValidationService.finishDataValidationProcessAndValidateData(goldenToken, platinumTokenLeft + platinumTokenRight, bindingModel);
        if (status.equals("no-permissions") ||
                status.equals("invalid-platinum-token")) {
            redirectAttributes.addFlashAttribute("goldenToken", goldenToken);
            redirectAttributes.addFlashAttribute("platinumTokenLeft", platinumTokenLeft);
            redirectAttributes.addFlashAttribute("platinumTokenRight", platinumTokenRight);
            redirectAttributes.addFlashAttribute("bindingModel", bindingModel);

            if(status.equals("invalid-platinum-token"))
                redirectAttributes.addFlashAttribute("invalidPlatinumToken", true);
            else
                redirectAttributes.addFlashAttribute("noPermissions", true);

            return "redirect:/identity/check/validate";
        }

        return "redirect:/identity/check/result/" + status;
    }

    @GetMapping("/identity/check/result/data-valid")
    public String getDataValidPage() {
        return "data-valid";
    }

    @GetMapping("/identity/check/result/data-invalid")
    public String getDataInvalidPage() {
        return "data-invalid";
    }
}
