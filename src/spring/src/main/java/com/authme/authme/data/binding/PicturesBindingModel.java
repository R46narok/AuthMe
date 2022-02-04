package com.authme.authme.data.binding;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PicturesBindingModel {
    private List<MultipartFile> picturesToSave;
    private List<String> savedPictures;

    public List<MultipartFile> getPicturesToSave() {
        return picturesToSave;
    }

    public PicturesBindingModel setPicturesToSave(List<MultipartFile> picturesToSave) {
        this.picturesToSave = picturesToSave;
        return this;
    }

    public List<String> getSavedPictures() {
        return savedPictures;
    }

    public PicturesBindingModel setSavedPictures(List<String> savedPictures) {
        this.savedPictures = savedPictures;
        return this;
    }
}
