package com.authme.authme.utils;

import com.authme.authme.data.dto.ProfileDTO;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Profile {
    private Long id;
    private ProfileDTO profileDTO;
    private List<Picture> pictures;
    private Long imageCounter;

    public Profile() {
        profileDTO = new ProfileDTO();
        imageCounter = 0L;
        pictures = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public Profile setId(Long id) {
        this.id = id;
        return this;
    }

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public Profile setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
        return this;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public Profile setPictures(List<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Long getImageCounter() {
        return imageCounter;
    }

    public void setImageCounter(Long imageCounter) {
        this.imageCounter = imageCounter;
    }

    public void incrementImageCounter() {
        this.imageCounter++;
    }
}
