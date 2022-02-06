package com.authme.authme.web;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.service.PersonalDataService;
import com.authme.authme.utils.Picture;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
public class ProfileController {
    private final PersonalDataService personalDataService;

    public ProfileController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @ModelAttribute(name = "profileBindingModel")
    public ProfileBindingModel profileBindingModel() {
        ProfileBindingModel bindingModel = personalDataService.getBindingModel();
        return bindingModel;
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }

    @PostMapping("/profile")
    public String patchProfile(ProfileBindingModel profileBindingModel) {
        personalDataService.patchProfile(profileBindingModel);
        return "redirect:/";
    }
}
