package com.authme.authme.web;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.enums.AuthMeUserRole;
import com.authme.authme.data.service.AuthMeUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Controller
public class AdminPanelController {
    private final AuthMeUserService userService;

    public AdminPanelController(AuthMeUserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("roles")
    public AuthMeUserRole[] getRoles() {
        return AuthMeUserRole.values();
    }

    @ModelAttribute("username")
    public String username() {
        return "";
    }

    @GetMapping("/admin")
    public String getAdminPanel() {
        return "admin-panel";
    }

    @GetMapping("/admin/roles/check")
    @ResponseBody
    public String getRolesForUsername(@RequestParam String username) {
        AuthMeUserEntity user = userService.getRolesOrNull(username);
        if(user == null)
            return "No such username";
        return user.getRoles().stream().map(r -> r.getName().name()).collect(Collectors.joining(", "));
    }

    @Transactional
    @PostMapping("/admin")
    public String changePermissions(@RequestParam String action,
                                    @RequestParam Integer role,
                                    @RequestParam String username,
                                    RedirectAttributes redirectAttributes) {
        if(role < AuthMeUserRole.values().length && role > 0){
            if(action.equals("add"))
                userService.setRole(username, role);
            else if(action.equals("remove"))
                userService.removeRole(username, role);
        }
        redirectAttributes.addFlashAttribute("username", username);
        return "redirect:/admin";
    }
}
