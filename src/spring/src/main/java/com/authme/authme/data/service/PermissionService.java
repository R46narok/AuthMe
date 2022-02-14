package com.authme.authme.data.service;

import com.authme.authme.data.entity.Permission;

import java.util.List;

public interface PermissionService {
    void init();

    List<Permission> getAll();

    Permission findById(Long valueOf);
}
