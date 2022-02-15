package com.authme.authme.data.service.impl;

import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.DataValidationRecordRepository;
import com.authme.authme.data.repository.GoldenTokenRepository;
import com.authme.authme.data.repository.PermissionRepository;
import com.authme.authme.data.service.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
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
    private final PersonalDataService personalDataService;

    public DataValidationRecordServiceImpl(DataValidationRecordRepository validationRepository,
                                           AuthMeUserRepository userRepository,
                                           PermissionRepository permissionRepository,
                                           Random random,
                                           IpLocatorService ipLocatorService,
                                           GoldenTokenService goldenTokenService,
                                           GoldenTokenRepository goldenTokenRepository, PersonalDataService personalDataService) {
        this.validationRepository = validationRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.random = random;
        this.ipLocatorService = ipLocatorService;
        this.goldenTokenService = goldenTokenService;
        this.goldenTokenRepository = goldenTokenRepository;
        this.personalDataService = personalDataService;
    }

    @Override
    public String triggerDataValidationProcess(String goldenToken, String issuer, String issuerIP) {
        GoldenToken token = goldenTokenService.findByIdOrThrow(goldenToken);
        AuthMeUserEntity user = token.getUser();

        if (token.getExpiry().isBefore(LocalDateTime.now()))
            return null;

        String platinumToken = String.valueOf(random.nextLong(platinumTokenLowerBound, platinumTokenUpperBound));
        String ipLocation = ipLocatorService.getLocationDetailsString(issuerIP);

        DataValidationRecord record = new DataValidationRecord()
                .setUser(user)
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

    @Override
    public String finishDataValidationProcessAndValidateData(String goldenToken, String platinumToken, ValidateProfileBindingModel bindingModel) {
        GoldenToken token = goldenTokenService.findByIdOrThrow(goldenToken);

        if (!hasPermissions(token, bindingModel)) {
            return "no-permissions";
        }

        boolean valid = personalDataService.checkDataValid(token.getUser(), bindingModel);



        return "";
    }

    private boolean hasPermissions(GoldenToken token, ValidateProfileBindingModel bindingModel) {
        Field[] fields = ValidateProfileBindingModel.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(bindingModel) != null &&
                        !field.get(bindingModel).equals("") &&
                    token.getPermissions().stream().noneMatch(p -> p.getFieldName().equals(field.getName()))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
