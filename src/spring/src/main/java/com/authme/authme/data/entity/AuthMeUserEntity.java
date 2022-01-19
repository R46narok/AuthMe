package com.authme.authme.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    @ManyToMany
    @NotNull
    private List<Role> roles;

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
}
