package com.authme.authme.web;

import com.authme.authme.data.binding.ManagerConsoleBindingModel;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

@Controller
public class TestController {
    @GetMapping("/test/bindingModel")
    @ResponseBody
    public ManagerConsoleBindingModel test() {
        return new ManagerConsoleBindingModel()
                .setIdentityId(1L)
                .setName("Valentin")
                .setMiddleName("Gurliov")
                .setSurname("Labadaichov")
                .setDateOfBirth(LocalDate.parse("2004-02-04"))
                .setFrontImage("1")
                .setBackImage("2");
    }

    @GetMapping("/test/image/{id}")
    public void getManagerImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Loading a random image in temp directory to be deleted after second request
        File temp = File.createTempFile("asd", "asd");
        temp.deleteOnExit();
        File image = new File("D:\\AuthMe\\src\\spring\\src\\main\\java\\com\\authme\\authme\\temp\\test.tmp");
        FileUtils.copyFile(image, temp);
        byte[] file = FileUtils.readFileToByteArray(temp);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("image/jpeg");
        response.setContentLength(file.length);
        OutputStream os = response.getOutputStream();
        os.write(file);
        os.close();
    }

    @PostMapping("/test/bindingModel")
    public String updateBindingModel(@RequestBody ManagerConsoleBindingModel bindingModel)  {
        System.out.println();
        return "redirect:/";
    }
}
