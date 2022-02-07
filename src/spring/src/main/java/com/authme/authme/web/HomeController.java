package com.authme.authme.web;
import com.authme.authme.data.service.AuthMeUserService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    private final RestTemplate restTemplate;
    private final AuthMeUserService userService;

    public HomeController(RestTemplateBuilder restTemplateBuilder, AuthMeUserService userService) {
        this.restTemplate = restTemplateBuilder.build();
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
