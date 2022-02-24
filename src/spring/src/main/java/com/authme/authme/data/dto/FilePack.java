package com.authme.authme.data.dto;

import java.io.File;

public class FilePack {
    private File temp;
    private String mimeType;

    public File getTemp() {
        return temp;
    }

    public FilePack setTemp(File temp) {
        this.temp = temp;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public FilePack setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }
}
