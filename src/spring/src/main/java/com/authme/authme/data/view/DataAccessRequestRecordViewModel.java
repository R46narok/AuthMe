package com.authme.authme.data.view;

public class DataAccessRequestRecordViewModel {
    private String name;
    private String ip;
    private String location;
    private String platinumToken;

    public String getName() {
        return name;
    }

    public DataAccessRequestRecordViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public DataAccessRequestRecordViewModel setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public DataAccessRequestRecordViewModel setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPlatinumToken() {
        return platinumToken;
    }

    public DataAccessRequestRecordViewModel setPlatinumToken(String platinumToken) {
        this.platinumToken = platinumToken;
        return this;
    }
}
