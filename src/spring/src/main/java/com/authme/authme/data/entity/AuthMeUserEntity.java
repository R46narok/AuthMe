package com.authme.authme.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
public class AuthMeUserEntity extends BaseEntity {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Long dataId;
    @OneToMany(mappedBy = "user")
    private List<GoldenToken> goldenTokens;
    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    private List<Role> roles;
    @OneToMany(mappedBy = "user")
    private List<DataValidationRecord> validationRecords;

    public String getUsername() {
        return username;
    }

    public AuthMeUserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthMeUserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Long getDataId() {
        return dataId;
    }

    public AuthMeUserEntity setDataId(Long dataId) {
        this.dataId = dataId;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public AuthMeUserEntity setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public List<DataValidationRecord> getValidationRecords() {
        return validationRecords;
    }

    public AuthMeUserEntity setValidationRecords(List<DataValidationRecord> validationRecords) {
        this.validationRecords = validationRecords;
        return this;
    }

    public List<GoldenToken> getGoldenTokens() {
        return goldenTokens;
    }

    public AuthMeUserEntity setGoldenTokens(List<GoldenToken> goldenTokens) {
        this.goldenTokens = goldenTokens;
        return this;
    }
}
