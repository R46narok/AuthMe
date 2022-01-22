package com.authme.authme.web;

import com.authme.authme.data.binding.ProfileBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ProfileController {
    @ModelAttribute(name = "profileBindingModel")
    public ProfileBindingModel profileBindingModel() {
        return new ProfileBindingModel();
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }
}
