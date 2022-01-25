package com.authme.authme.data.service.external.impl;

import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.service.external.PersonalDataService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

// TODO: Connect with ASP.NET secondary service
@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    @Override
    public Long newEntry() {
        return 0L;
    }

    // TODO: change return type to a new DTO type
    @Override
    public ProfileDTO getData(Long dataId) {
        return new ProfileDTO();
    }



}
