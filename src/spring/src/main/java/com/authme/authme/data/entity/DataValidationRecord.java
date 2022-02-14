package com.authme.authme.data.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class DataValidationRecord extends BaseEntity {
    @ManyToOne
    private AuthMeUserEntity user;
    // Requester
    private String name;
    private String ip;
    private String location;
    private String platinumToken;
    @ManyToMany
    @JoinTable(
            name = "validation_record_permissions",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> allowedPermissions;


    public AuthMeUserEntity getUser() {
        return user;
    }

    public DataValidationRecord setUser(AuthMeUserEntity user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public DataValidationRecord setName(String name) {
        this.name = name;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public DataValidationRecord setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public DataValidationRecord setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPlatinumToken() {
        return platinumToken;
    }

    public DataValidationRecord setPlatinumToken(String platinumToken) {
        this.platinumToken = platinumToken;
        return this;
    }

    public List<Permission> getAllowedPermissions() {
        return allowedPermissions;
    }

    public DataValidationRecord setAllowedPermissions(List<Permission> allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
        return this;
    }
}
