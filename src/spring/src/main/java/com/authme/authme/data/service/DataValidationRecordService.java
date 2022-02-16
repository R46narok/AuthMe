package com.authme.authme.data.service;

import com.authme.authme.data.binding.ValidateProfileBindingModel;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.view.DataMonitorViewModel;

public interface DataValidationRecordService {
    DataMonitorViewModel getDataMonitorViewModel();

    String triggerDataValidationProcess(String goldenToken, String issuer, String issuerIP);

    String finishDataValidationProcessAndValidateData(String goldenToken, String platinumToken, ValidateProfileBindingModel bindingModel);
}
