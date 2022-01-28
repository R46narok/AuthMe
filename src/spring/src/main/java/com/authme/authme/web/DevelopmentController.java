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

    @PostMapping("/dev/profile")
    public ProfileDTO patchProfileData(@RequestHeader(name = "dataId") Long dataId,
                                       @RequestBody ProfileDTO profileDTO) {
        ProfileDTO oldData = data.get(dataId);
        if(!oldData.getFirstName().equals(profileDTO.getFirstName())){
            profileDTO.setFirstNameValidated(false);
        }
        if(profileDTO.getFirstName().equals("")){
            profileDTO.setFirstNameValidated(true);
        }

        if(!oldData.getMiddleName().equals(profileDTO.getMiddleName())){
            profileDTO.setMiddleNameValidated(false);
        }
        if(profileDTO.getMiddleName().equals("")){
            profileDTO.setMiddleNameValidated(true);
        }

        if(!oldData.getLastName().equals(profileDTO.getLastName())){
            profileDTO.setLastNameValidated(false);
        }
        if(profileDTO.getLastName().equals("")){
            profileDTO.setLastNameValidated(true);
        }

        if(profileDTO.getDateOfBirth() == null)
            profileDTO.setDateOfBirthValidated(true);
        else if(!profileDTO.getDateOfBirth().equals(oldData.getDateOfBirth())){
            profileDTO.setDateOfBirthValidated(false);
        }
    
        data.put(dataId, profileDTO);
        return data.get(dataId);
    }

    @GetMapping("/dev/entry")
    public String createEntry() {
        Long dataId = random.nextLong();
        data.put(dataId, new ProfileDTO());
        return dataId.toString();
    }
}
