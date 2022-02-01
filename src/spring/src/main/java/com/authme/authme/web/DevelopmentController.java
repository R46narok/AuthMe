package com.authme.authme.web;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.utils.Picture;
import com.authme.authme.utils.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.util.*;

@RestController
public class DevelopmentController {
    private Map<Long, Profile> data = new LinkedHashMap<>();
    private Long dataId = 0L;

    private Long getDataId() {
        return dataId++;
    }

    @GetMapping("/dev/entry")
    public String createEntry() {
        Long dataId = getDataId();
        data.put(dataId, new Profile());
        return dataId.toString();
    }

    @GetMapping("/dev/profile")
    public ProfileDTO getProfileData(@RequestHeader(name = "dataId") Long dataId) {
        return data.get(dataId).getProfileDTO();
    }

    @PostMapping("/dev/profile/picture/{userId}")
    public ResponseEntity<String> uploadPicture(@PathVariable(name = "userId") Long userId,
                                                @RequestParam("pictures") List<MultipartFile> pictures) throws IOException {
        Profile profile = data.get(userId);
        for (MultipartFile picture : pictures) {
            File temp = File.createTempFile("user" + userId + "-", ".tmp");
            temp.deleteOnExit();
            picture.transferTo(temp);
            profile.getPictures().add(new Picture().setPicture(temp).setContentType(picture.getContentType()));
        }
        return ResponseEntity.created(URI.create("/dev/profile/picture/" + userId)).build();
    }

    @GetMapping("/dev/profile/picture/{userId}")
    public List<String> getPictures(@PathVariable(name = "userId") Long userId) {
        Profile profile = data.get(userId);
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < profile.getPictures().size(); ++i) {
            paths.add(String.valueOf(i));
        }
        return paths;
    }

    @GetMapping("/dev/profile/picture/{userId}/{pictureId}")
    public void getPicture(@PathVariable("userId") Long userId,
                           @PathVariable("pictureId") Integer pictureId,
                           HttpServletResponse response) throws IOException {
        Picture picture = data.get(userId).getPictures().get(pictureId);
        response.setContentType(picture.getContentType());
        StreamUtils.copy(new FileInputStream(picture.getPicture()), response.getOutputStream());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/dev/profile", consumes = {"multipart/form-data"})
    public ProfileDTO patchProfileData(@RequestHeader(name = "dataId") Long dataId,
                                       @RequestPart("body") ProfileDTO profileDTO) {
        Profile profile = data.get(dataId);
        profile.setProfileDTO(profileDTO);
        return data.get(dataId).getProfileDTO();
    }
}
