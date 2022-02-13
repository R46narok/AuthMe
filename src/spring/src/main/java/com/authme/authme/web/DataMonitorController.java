package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.view.DataMonitorViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;

@Controller
public class DataMonitorController {
    private final AuthMeUserService userService;

    public DataMonitorController(AuthMeUserService userService) {
        this.userService = userService;
    }

    @Transactional
    @ModelAttribute("dataMonitorViewModel")
    public DataMonitorViewModel getViewModel() {
        return userService.getDataMonitorViewModel();
    }

    @GetMapping("/data")
    public String getPage() {
        return "monitor";
    }
}
