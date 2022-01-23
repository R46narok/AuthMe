package com.authme.authme.web;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.service.AuthMeUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProfileController {
    private final AuthMeUserService userService;

    public ProfileController(AuthMeUserService userService) {
        this.userService = userService;
    }


    @ModelAttribute(name = "profileBindingModel")
    public ProfileBindingModel profileBindingModel() {
        return userService.getProfileBindingModel();
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }
}
