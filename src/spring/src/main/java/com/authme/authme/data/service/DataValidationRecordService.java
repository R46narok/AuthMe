package com.authme.authme.data.service;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;

public interface DataValidationRecordService {
    String triggerDataValidationProcess(String goldenToken, String issuer, String issuerIP);
}
