package com.authme.authme.data.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GoldenTokenView {
    private String id;
    private LocalDateTime expiry;
    private List<PermissionViewModel> permissions;

    public GoldenTokenView() {
        permissions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public GoldenTokenView setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public GoldenTokenView setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
        return this;
    }

    public List<PermissionViewModel> getPermissions() {
        return permissions;
    }

    public GoldenTokenView setPermissions(List<PermissionViewModel> permissions) {
        this.permissions = permissions;
        return this;
    }
}
