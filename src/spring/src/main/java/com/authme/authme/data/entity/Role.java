package com.authme.authme.data.entity;

import com.authme.authme.data.entity.enums.AuthMeUserRole;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthMeUserRole name;

    public AuthMeUserRole getName() {
        return name;
    }

    public Role setName(AuthMeUserRole name) {
        this.name = name;
        return this;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name.name().toUpperCase();
    }
}
