package com.authme.authme.init;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.data.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
    private final RoleService roleService;
    private final AuthMeUserService userService;
    private final PermissionService permissionService;

    public Init(RoleService roleService, AuthMeUserService userService, PermissionService permissionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public void run(String... args) throws Exception {
        permissionService.init();
        roleService.init();
        userService.init();
    }
}
