package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.DataValidationRecordRepository;
import com.authme.authme.data.service.DataValidationRecordService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DataValidationRecordServiceImpl implements DataValidationRecordService {
    private final DataValidationRecordRepository validationRepository;
    private final AuthMeUserRepository userRepository;

    public DataValidationRecordServiceImpl(DataValidationRecordRepository validationRepository, AuthMeUserRepository userRepository) {
        this.validationRepository = validationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DataValidationRecord generateRecord(AuthMeUserEntity user, String requesterName, String remoteAddress) {
        DataValidationRecord record = new DataValidationRecord()
                .setUser(user)
                .setAllowedPermissions(user.getGoldenToken().getPermissions())
                .setRequesterName(requesterName)
                .setRequesterIpAddress(remoteAddress)
                .setPlatinumToken(UUID.randomUUID().toString());
        record = validationRepository.saveAndFlush(record);
        user.getValidationRecords().add(record);
        userRepository.saveAndFlush(user);
        return record;
    }
}
