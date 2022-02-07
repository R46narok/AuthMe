package com.authme.authme.data.dto;

import java.util.Map;

public class DataVerificationDTO {
    private String goldenToken;
    private Map<String, String> data;

    public String getGoldenToken() {
        return goldenToken;
    }

    public DataVerificationDTO setGoldenToken(String goldenToken) {
        this.goldenToken = goldenToken;
        return this;
    }

    public Map<String, String> getData() {
        return data;
    }

    public DataVerificationDTO setData(Map<String, String> data) {
        this.data = data;
        return this;
    }
}
