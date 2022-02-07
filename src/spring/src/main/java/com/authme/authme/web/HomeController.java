package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    private final AuthMeUserService userService;

    public HomeController(AuthMeUserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("activeToken")
    public Boolean activeToken() {
        return userService.goldenTokenActive();
    }

    @ModelAttribute("goldenToken")
    public String goldenToken() {
        return userService.getCurrentUserGoldenToken();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/token/golden")
    public String generateGoldenToken() {
        userService.generateGoldenToken();
        return "redirect:/";
    }
}
