package com.authme.authme.init;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
    private final RoleService roleService;
    private final AuthMeUserService userService;

    public Init(RoleService roleService, AuthMeUserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.init();
        userService.init();
    }
}
