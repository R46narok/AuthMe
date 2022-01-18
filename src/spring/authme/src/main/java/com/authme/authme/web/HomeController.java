package com.authme.authme.web;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {
    private final RestTemplate restTemplate;

    public HomeController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String post() {
        String value = restTemplate.getForObject("http://85.187.94.176:5236/", String.class);

        System.out.println(value);

        return "redirect:/";
    }

}
