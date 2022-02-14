package com.authme.authme.data.dto;

public class LocationDTO {
    private String status;
    private String city;
    private String country;
    private String lat;
    private String lon;
    private String isp;

    public LocationDTO() {
        status = "";
        city = "";
        country = "";
        lat = "";
        lon = "";
        isp = "";
    }

    public String getStatus() {
        return status;
    }

    public LocationDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCity() {
        return city;
    }

    public LocationDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public LocationDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public LocationDTO setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLon() {
        return lon;
    }

    public LocationDTO setLon(String lon) {
        this.lon = lon;
        return this;
    }

    public String getIsp() {
        return isp;
    }

    public LocationDTO setIsp(String isp) {
        this.isp = isp;
        return this;
    }
}
