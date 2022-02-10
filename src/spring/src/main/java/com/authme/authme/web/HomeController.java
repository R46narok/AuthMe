package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.data.view.PermissionViewModel;
import com.authme.authme.utils.ClassMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
