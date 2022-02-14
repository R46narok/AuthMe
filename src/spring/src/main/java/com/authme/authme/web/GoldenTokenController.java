package com.authme.authme.web;

import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.GoldenTokenService;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.data.view.GoldenTokenView;
import com.authme.authme.data.view.PermissionViewModel;
import com.authme.authme.utils.ClassMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GoldenTokenController {
    private final AuthMeUserService userService;
    private final GoldenTokenService goldenTokenService;
    private final PermissionService permissionService;
    private final ClassMapper classMapper;

    public GoldenTokenController(AuthMeUserService userService, GoldenTokenService goldenTokenService, PermissionService permissionService, ClassMapper classMapper) {
        this.userService = userService;
        this.goldenTokenService = goldenTokenService;
        this.permissionService = permissionService;
        this.classMapper = classMapper;
    }

    @Transactional
    @ModelAttribute("tokens")
    public List<GoldenTokenView> goldenTokens() {
        return userService.getCurrentUserGoldenTokens();
    }

    @ModelAttribute("permissions")
    public List<PermissionViewModel> permissionViewModels() {
        return classMapper.toPermissionViewModelList(permissionService.getAll());
    }

    @GetMapping("/token")
    public String getPage() {
        return "token";
    }

    @Transactional
    @GetMapping("/token/golden/generate")
    public String generateGoldenToken() {
        userService.generateGoldenToken();
        return "redirect:/token";
    }

    @Transactional
    @PostMapping("/token/golden/permission")
    public String changeCurrentTokenPermissions(@RequestParam String goldenToken,
                                                @RequestParam(value = "permission", required = false) List<String> permissionsStrings) {
        if (permissionsStrings == null)
            permissionsStrings = new ArrayList<>();
        if(userService.tokenBelongsToCurrentUser(goldenToken)) {
            goldenTokenService.setTokenPermissions(goldenToken, permissionsStrings);
        }
        return "redirect:/token";
    }
}
