package com.authme.authme.web;

import com.authme.authme.data.binding.ManagerConsoleBindingModel;
import com.authme.authme.data.dto.FilePack;
import com.authme.authme.data.service.PersonalDataService;
import org.apache.commons.io.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class ManagerConsoleController {
    private final PersonalDataService personalDataService;

    public ManagerConsoleController(PersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @ModelAttribute("bindingModel")
    public ManagerConsoleBindingModel bindingModel() {
        ManagerConsoleBindingModel bindingModel = personalDataService.getNewManagerConsoleBindingModel();
        bindingModel.setFrontImage("/manager/image/" + bindingModel.getFrontImage());
        bindingModel.setBackImage("/manager/image/" + bindingModel.getBackImage());
        return bindingModel;
    }

    @GetMapping("/manager/console")
    public String getPage() {
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
