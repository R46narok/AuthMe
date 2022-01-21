package com.authme.authme.web;

import com.authme.authme.data.binding.LoginBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @ModelAttribute(name = "loginBindingModel")
    public LoginBindingModel loginBindingModel() {return new LoginBindingModel();}
    @ModelAttribute(name = "error")
    public boolean error() { return false; }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login-error")
    public String loginError(LoginBindingModel bindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        bindingModel.setPassword("");
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginBindingModel", bindingResult);
        redirectAttributes.addFlashAttribute("loginBindingModel", bindingModel);
        redirectAttributes.addFlashAttribute("error", true);
        return "redirect:/login";
    }

}
