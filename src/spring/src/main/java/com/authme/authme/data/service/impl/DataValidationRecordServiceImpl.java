package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.DataValidationRecordRepository;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.DataValidationRecordService;
import com.authme.authme.data.service.IpLocatorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DataValidationRecordServiceImpl implements DataValidationRecordService {
    private final DataValidationRecordRepository validationRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final Random random;
    private final IpLocatorService ipLocatorService;
    private static final Long platinumTokenLowerBound = 1000000000000000L;
    private static final Long platinumTokenUpperBound = 9999999999999999L;

    public DataValidationRecordServiceImpl(DataValidationRecordRepository validationRepository, AuthMeUserRepository userRepository, PermissionRepository permissionRepository, Random random, IpLocatorService ipLocatorService) {
        this.validationRepository = validationRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.random = random;
        this.ipLocatorService = ipLocatorService;
    }

    @Override
    public DataValidationRecord generateRecord(AuthMeUserEntity user, String requesterName, String remoteAddress) {
        List<Permission> permissions = new ArrayList<>();

        for (Permission permission : user.getGoldenToken().getPermissions()) {
            permissions.add(permissionRepository.findById(permission.getId()).get());
        }

        String platinumToken = String.valueOf(random.nextLong(platinumTokenLowerBound, platinumTokenUpperBound));
        String ipLocation = ipLocatorService.getLocationDetailsString(remoteAddress);

        DataValidationRecord record = new DataValidationRecord()
                .setUser(user)
                .setAllowedPermissions(permissions)
                .setName(requesterName)
                .setIp(remoteAddress)
                .setPlatinumToken(platinumToken)
                .setLocation(ipLocation);
        record = validationRepository.saveAndFlush(record);

        user.getValidationRecords().add(record);
        userRepository.saveAndFlush(user);

        return record;
    }
}
