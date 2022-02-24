package com.authme.authme.web;

import org.apache.commons.io.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class ManagerConsole {
    private File prev = null;

    @GetMapping("/manager/console")
    @PreAuthorize("isManager()")
    public String getPage() {
        return "manager-console";
    }

    @GetMapping("/test/file")
    public void getManagerImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(prev != null)
            prev.delete();
        File temp = File.createTempFile("asd", "asd");
        temp.deleteOnExit();
        File image = new File("D:\\AuthMe\\src\\spring\\src\\main\\java\\com\\authme\\authme\\temp\\test.tmp");
        FileUtils.copyFile(image, temp);
        byte[] file = FileUtils.readFileToByteArray(temp);

        System.out.println(temp.toPath());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("image/png");
        response.setContentLength(file.length);
        OutputStream os = response.getOutputStream();

        os.write(file);
        prev = temp;
    }
}
