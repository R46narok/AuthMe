package com.authme.authme.utils;

import java.io.File;

public class Picture {
    private File picture;
    private String contentType;

    public File getPicture() {
        return picture;
    }

    public Picture setPicture(File picture) {
        this.picture = picture;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public Picture setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
}
