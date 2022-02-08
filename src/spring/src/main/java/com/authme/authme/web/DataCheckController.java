package com.authme.authme.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataCheckController {
    @GetMapping("/identity/check")
    public String getPage() {
        return "identity-check";
    }
}
