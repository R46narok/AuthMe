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
    private final AuthMeUserService userService;
    private final PermissionService permissionService;
    private final ClassMapper classMapper;

    public HomeController(AuthMeUserService userService, PermissionService permissionService, ClassMapper classMapper) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.classMapper = classMapper;
    }

    @ModelAttribute("activeToken")
    public Boolean activeToken() {
        return userService.goldenTokenActive();
    }

    @ModelAttribute("goldenToken")
    public String goldenToken() {
        return userService.getCurrentUserGoldenToken();
    }

    @ModelAttribute("permissions")
    public List<PermissionViewModel> getPermission() {
        return classMapper.toPermissionViewModelList(permissionService.getAll());
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

    @PostMapping("/token/golden/permission")
    public String changeCurrentTokenPermissions(@RequestParam("permission") List<String> permissionsStrings) {
        System.out.println();
        return "redirect:/";
    }
}
