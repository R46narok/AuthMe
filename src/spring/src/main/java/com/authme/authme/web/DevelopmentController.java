package com.authme.authme.web;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.utils.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DevelopmentController {
    private Long recordIdCounter = 0L;
    private Map<Long, Profile> data = new HashMap<>();

    @GetMapping("/dev/profile/create")
    public ResponseEntity<Long> createRecord() {
        Long newId = getNewRecordId();
        Profile profile = new Profile();
        profile.getData().getDateOfBirth().setValue(LocalDate.now());
        data.put(newId, profile);
        return ResponseEntity.ok(newId);
    }

    @GetMapping("/dev/profile/{dataId}")
    public ResponseEntity<ProfileDTO> getProfileData(@PathVariable Long dataId) {
        Profile profile = data.get(dataId);
        return ResponseEntity.ok(profile.getData());
    }

    @PostMapping("/dev/profile/{dataId}")
    public ResponseEntity<ProfileDTO> updateProfileData(@PathVariable Long dataId,
                                                        @RequestBody ProfileDTO body) {
        Profile profile = data.get(dataId);
        profile.getData().update(body);
        return ResponseEntity.ok(profile.getData());
    }

    @PostMapping("/dev/profile/validation/{dataId}")
    public ResponseEntity<Void> uploadVerificationPicture(@PathVariable Long dataId,
                                                          @RequestPart("pictures") List<MultipartFile> pictures) {
        System.out.println();
        return ResponseEntity.ok().build();
    }

    private Long getNewRecordId() {
        return recordIdCounter++;
    }
}
