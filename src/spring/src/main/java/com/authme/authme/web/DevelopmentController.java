package com.authme.authme.web;

import com.authme.authme.data.dto.ProfileDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class DevelopmentController {
    private Map<Long, ProfileDTO> data = new LinkedHashMap<>();
    Random random = new Random();

//    @PostMapping("/dev/test")
//    public void test(@RequestPart("images") List<MultipartFile> images) {
//        System.out.println();
//    }

    @GetMapping("/dev/profile")
    public ProfileDTO getProfileData(@RequestHeader(name = "dataId") Long dataId) {
        return data.get(dataId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/dev/profile", consumes = {"multipart/form-data"})
    public ProfileDTO patchProfileData(@RequestHeader(name = "dataId") Long dataId,
                                       @RequestPart("body") ProfileDTO profileDTO,
                                       @RequestPart(value = "pictures", required = false) List<MultipartFile> pictures) {
        
        data.put(dataId, profileDTO);
        return data.get(dataId);
    }

    @GetMapping("/dev/entry")
    public String createEntry() throws FileNotFoundException {
        Long dataId = random.nextLong();
        data.put(dataId, new ProfileDTO());
        return dataId.toString();
    }
}
