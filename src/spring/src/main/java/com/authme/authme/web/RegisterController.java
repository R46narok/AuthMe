package com.authme.authme.web;

import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.service.AuthMeUserService;
import com.authme.authme.data.service.models.RegisterServiceModel;
import com.authme.authme.utils.impl.ClassMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {
    private final AuthMeUserService userService;

    public RegisterController(AuthMeUserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "registerBindingModel")
    public RegisterBindingModel registerBindingModel() {
        return new RegisterBindingModel();
    }

    @ModelAttribute(name = "passwordsDontMatch")
    public boolean passwordsDontMatch() { return false; }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterBindingModel bindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors() || bindingModel.passwordsDontMatch()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("registerBindingModel", bindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", bindingModel.passwordsDontMatch());
            return "redirect:/register";
        }

        RegisterServiceModel registerServiceModel = ClassMapper.registerBindingToService(bindingModel);
        userService.registerAndLogin(registerServiceModel);

        return "redirect:/";
    }
}
