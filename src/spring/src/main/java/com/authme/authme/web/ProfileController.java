package com.authme.authme.web;

import com.authme.authme.data.binding.ChangePasswordBindingModel;
import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.PersonalDataService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController {
    private final PersonalDataService personalDataService;
    private final AuthMeUserService userService;

    public ProfileController(PersonalDataService personalDataService, AuthMeUserService userService) {
        this.personalDataService = personalDataService;
        this.userService = userService;
    }

    @ModelAttribute("profileBindingModel")
    public ProfileBindingModel profileBindingModel() {
        return personalDataService.getBindingModel();
    }

    @ModelAttribute("incorrectPassword")
    public boolean incorrectPassword() {
        return false;
    }

    @ModelAttribute("passwordsDontMatch")
    public boolean passwordsDontMatch() {
        return false;
    }

    @ModelAttribute("changePasswordBindingModel")
    public ChangePasswordBindingModel getChangePasswordBindingModel() {
        return new ChangePasswordBindingModel();
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }

    @PostMapping("/profile")
    public String patchProfile(ProfileBindingModel profileBindingModel) {
        personalDataService.patchProfile(profileBindingModel);
        return "redirect:/profile";
    }

    @PostMapping("/profile/validation")
    public String uploadPictures(@RequestPart MultipartFile frontImage, @RequestPart MultipartFile backImage) {
        personalDataService.uploadIdCardPictures(frontImage, backImage);
        return "redirect:/profile";
    }

    @PostMapping("/profile/change/password")
    public String changePassword(@Valid ChangePasswordBindingModel changePasswordBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors() || changePasswordBindingModel.passwordsDontMatch()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", changePasswordBindingModel.passwordsDontMatch());
        }
        userService.updatePassword(changePasswordBindingModel.getNewPassword());
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/profile";
    }
}
