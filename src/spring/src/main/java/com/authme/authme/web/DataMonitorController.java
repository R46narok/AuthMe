package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.view.DataMonitorViewModel;
import org.springframework.stereotype.Controller;

@Controller
public class DataMonitorController {
    private final AuthMeUserService userService;

    public DataMonitorController(AuthMeUserService userService) {
        this.userService = userService;
    }

    public DataMonitorViewModel getViewModel() {
        userService.getDataMonitorViewModel();
    }

    public String getPage() {
        return "data-monitor";
    }
}
