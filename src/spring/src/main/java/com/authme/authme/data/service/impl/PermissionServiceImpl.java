package com.authme.authme.data.service.impl;

import com.authme.authme.data.dto.ProfileDTOGet;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.PermissionService;
import com.authme.authme.exceptions.CommonErrorMessages;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void init() {
        if (permissionRepository.count() == 0) {
            Field[] fieldsInDTO = ProfileDTOGet.class.getDeclaredFields();
            for (Field field : fieldsInDTO) {
                Permission permission = new Permission().setFieldName(field.getName());
                permissionRepository.saveAndFlush(permission);
            }
        }
    }

    @Override
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission findById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> CommonErrorMessages.permission(id));
    }
}
