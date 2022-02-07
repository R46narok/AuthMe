package com.authme.authme.data.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class DataValidationRecord extends BaseEntity {
    @ManyToOne
    private AuthMeUserEntity user;
    private String requesterName;
    private String requesterIpAddress;
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

    public String getRequesterName() {
        return requesterName;
    }

    public DataValidationRecord setRequesterName(String requesterName) {
        this.requesterName = requesterName;
        return this;
    }

    public String getRequesterIpAddress() {
        return requesterIpAddress;
    }

    public DataValidationRecord setRequesterIpAddress(String requesterIpAddress) {
        this.requesterIpAddress = requesterIpAddress;
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
