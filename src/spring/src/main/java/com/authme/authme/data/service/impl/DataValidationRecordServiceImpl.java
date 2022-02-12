package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.DataValidationRecordRepository;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.DataValidationRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DataValidationRecordServiceImpl implements DataValidationRecordService {
    private final DataValidationRecordRepository validationRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public DataValidationRecordServiceImpl(DataValidationRecordRepository validationRepository, AuthMeUserRepository userRepository, PermissionRepository permissionRepository) {
        this.validationRepository = validationRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public DataValidationRecord generateRecord(AuthMeUserEntity user, String requesterName, String remoteAddress) {
        List<Permission> permissions = new ArrayList<>();

        for (Permission permission : user.getGoldenToken().getPermissions()) {
            permissions.add(permissionRepository.findById(permission.getId()).get());
        }

        DataValidationRecord record = new DataValidationRecord()
                .setUser(user)
                .setAllowedPermissions(permissions)
                .setRequesterName(requesterName)
                .setRequesterIpAddress(remoteAddress)
                .setPlatinumToken(UUID.randomUUID().toString());
        record = validationRepository.saveAndFlush(record);
        user.getValidationRecords().add(record);
        userRepository.saveAndFlush(user);
        return record;
    }
}
