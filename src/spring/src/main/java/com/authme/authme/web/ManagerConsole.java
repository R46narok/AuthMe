package com.authme.authme.web;

import org.apache.commons.io.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Controller
public class ManagerConsole {
    @GetMapping("/manager/console")
    @PreAuthorize("isManager()")
    public String getPage() {
        return "manager-console";
    }

    @GetMapping("/test/file")
    public @ResponseBody byte[] getManagerImage(HttpServletResponse response) throws IOException {
        File file = File.createTempFile("", "");

        response.setContentType("image/jpeg");
        return FileUtils.readFileToByteArray(new File("/temp/test.tmp"));
    }
}
