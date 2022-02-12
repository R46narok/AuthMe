package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.GoldenTokenService;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.data.view.PermissionViewModel;
import com.authme.authme.utils.ClassMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GoldenTokenController {
    private final AuthMeUserService userService;
    private final ClassMapper classMapper;
    private final PermissionService permissionService;
    private final GoldenTokenService goldenTokenService;

    public GoldenTokenController(AuthMeUserService userService, ClassMapper classMapper, PermissionService permissionService, GoldenTokenService goldenTokenService) {
        this.userService = userService;
        this.classMapper = classMapper;
        this.permissionService = permissionService;
        this.goldenTokenService = goldenTokenService;
    }

    @ModelAttribute("activeToken")
    public Boolean activeToken() {
        return userService.goldenTokenActive();
    }

    @ModelAttribute("goldenToken")
    public String goldenToken() {
        return userService.getCurrentUserGoldenToken();
    }

    @ModelAttribute("goldenTokenExpiry")
    public LocalDateTime goldenTokenExpiry() {
        return userService.getCurrentUserGoldenTokenExpiry();
    }

    @Transactional
    @ModelAttribute("permissions")
    public List<PermissionViewModel> getPermission() {
        return goldenTokenService.getAllPermissionsTagged();
    }

    @GetMapping("/token")
    public String getPage(){
        return "token";
    }


    @GetMapping("/token/golden/generate")
    public String generateGoldenToken() {
        userService.generateGoldenToken();
        return "redirect:/token";
    }


    @PostMapping("/token/golden/permission")
    public String changeCurrentTokenPermissions(@RequestParam(value = "permission", required = false) List<String> permissionsStrings) {
        if(permissionsStrings == null)
            permissionsStrings = new ArrayList<>();
        userService.setTokenPermissions(permissionsStrings);
        return "redirect:/token";
    }
}
