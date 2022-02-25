package com.authme.authme.web;

import com.authme.authme.data.binding.ManagerConsoleBindingModel;
import com.authme.authme.data.dto.FilePack;
import com.authme.authme.data.service.PersonalDataService;
import org.apache.commons.io.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpClient;

@Controller
public class ManagerConsoleController {
    private final PersonalDataService personalDataService;

    public ManagerConsoleController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @GetMapping("/manager/console")
    public String getPage(Model model) {
        try {
            ManagerConsoleBindingModel bindingModel = personalDataService.getNewManagerConsoleBindingModel();
            bindingModel.setFrontImage("/manager/image/front-" + bindingModel.getIdentityId());
            bindingModel.setBackImage("/manager/image/back-" + bindingModel.getIdentityId());
            model.addAttribute("bindingModel", bindingModel);
        } catch (HttpClientErrorException ex) {
            ManagerConsoleBindingModel bindingModel = new ManagerConsoleBindingModel();
            bindingModel.setFrontImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Gull_portrait_ca_usa.jpg/1280px-Gull_portrait_ca_usa.jpg");
            bindingModel.setBackImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Gull_portrait_ca_usa.jpg/1280px-Gull_portrait_ca_usa.jpg");
            model.addAttribute("bindingModel", bindingModel);
        }

        return "manager-console";
    }

    @GetMapping("/manager/image/{id}")
    public void getManagerImage(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable String id) throws IOException {
        //Loading a random image in temp directory to be deleted after second request
        FilePack filePack = personalDataService.getImage(id);
        byte[] file = FileUtils.readFileToByteArray(filePack.getTemp());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(filePack.getMimeType());
        response.setContentLength(file.length);
        OutputStream os = response.getOutputStream();
        os.write(file);
        os.close();
    }

    @PostMapping("/manager/console")
    public String updateBindingModel(ManagerConsoleBindingModel bindingModel)
            throws IOException {
        personalDataService.updateBindingModel(bindingModel);
        return "redirect:/manager/console";
    }

}
