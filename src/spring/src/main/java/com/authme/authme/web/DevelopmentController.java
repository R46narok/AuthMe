package com.authme.authme.web;

import com.authme.authme.data.dto.ProfileDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class DevelopmentController {
    private Map<Long, ProfileDTO> data = new LinkedHashMap<>();
    Random random = new Random();

    @GetMapping("/dev/profile")
    public ProfileDTO getProfileData(@RequestHeader(name = "dataId") Long dataId) {
        return data.get(dataId);
    }

    @GetMapping("/dev/entry")
    public String createEntry() {
        Long dataId = random.nextLong();
        data.put(dataId, new ProfileDTO().setDateOfBirth(LocalDate.now()).setLastName("Serverov"));
        return dataId.toString();
    }
}
