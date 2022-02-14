package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.DataValidationRecordRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DataValidationRecordServiceImpl implements DataValidationRecordService {
    private static final Long platinumTokenLowerBound = 1000000000000000L;
    private static final Long platinumTokenUpperBound = 9999999999999999L;

    private final DataValidationRecordRepository validationRepository;
    private final AuthMeUserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final Random random;
    private final IpLocatorService ipLocatorService;
    private final GoldenTokenService goldenTokenService;
    private final GoldenTokenRepository goldenTokenRepository;

    public DataValidationRecordServiceImpl(DataValidationRecordRepository validationRepository,
                                           AuthMeUserRepository userRepository,
                                           PermissionRepository permissionRepository,
                                           Random random,
                                           IpLocatorService ipLocatorService,
                                           GoldenTokenService goldenTokenService,
                                           GoldenTokenRepository goldenTokenRepository) {
        this.validationRepository = validationRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.random = random;
        this.ipLocatorService = ipLocatorService;
        this.goldenTokenService = goldenTokenService;
        this.goldenTokenRepository = goldenTokenRepository;
    }

    @Override
    public String triggerDataValidationProcess(String goldenToken, String issuer, String issuerIP) {
        GoldenToken token = goldenTokenService.findByIdOrThrow(goldenToken);
        AuthMeUserEntity user = token.getUser();

        if (token.getExpiry().isBefore(LocalDateTime.now()))
            return null;

        List<Permission> permissions = new ArrayList<>();

        for (Permission permission : token.getPermissions()) {
            permissions.add(permissionRepository.findById(permission.getId()).get());
        }

        String platinumToken = String.valueOf(random.nextLong(platinumTokenLowerBound, platinumTokenUpperBound));
        String ipLocation = ipLocatorService.getLocationDetailsString(issuerIP);

        DataValidationRecord record = new DataValidationRecord()
                .setUser(user)
                .setAllowedPermissions(permissions)
                .setName(issuer)
                .setIp(issuerIP)
                .setPlatinumToken(platinumToken)
                .setLocation(ipLocation);
        record = validationRepository.saveAndFlush(record);

        user.getValidationRecords().add(record);
        userRepository.saveAndFlush(user);

        token.setExpiry(LocalDateTime.now());
        goldenTokenRepository.saveAndFlush(token);

        return record.getPlatinumToken().substring(0, record.getPlatinumToken().length() / 2);
    }
}
