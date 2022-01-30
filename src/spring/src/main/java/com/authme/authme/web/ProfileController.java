package com.authme.authme.web;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.AuthMeUserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    @PostMapping("/profile")
    public String patchProfile(ProfileBindingModel profileBindingModel) {
        userService.patchProfile(profileBindingModel);
        return "redirect:/";
    }

    @GetMapping(value = "/profile/pictures/{id}")
    public void getPicture(AuthenticatedPrincipal principal,
                           @PathVariable("id") Integer pictureId,
                           HttpServletResponse response) throws IOException {
        File temp = userService.getImage(principal, pictureId);
        StreamUtils.copy(new FileInputStream(temp), response.getOutputStream());
    }
}
