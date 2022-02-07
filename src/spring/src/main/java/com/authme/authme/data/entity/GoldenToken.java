package com.authme.authme.data.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "golden_tokens")
public class GoldenToken {
    @Id
    @Column(nullable = false)
    private String id;
    @OneToOne(optional = false)
    private AuthMeUserEntity user;
    @Column(nullable = false)
    private LocalDateTime expiry;
    @ManyToMany
    @JoinTable(
            name = "token_permissions",
            joinColumns = @JoinColumn(name = "token_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;

    public String getId() {
        return id;
    }

    public GoldenToken setId(String id) {
        this.id = id;
        return this;
    }

    public AuthMeUserEntity getUser() {
        return user;
    }

    public GoldenToken setUser(AuthMeUserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public GoldenToken setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public GoldenToken setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }
}
