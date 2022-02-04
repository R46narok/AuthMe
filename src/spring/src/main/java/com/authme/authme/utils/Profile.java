package com.authme.authme.utils;

import com.authme.authme.data.dto.ProfileDTO;

public class Profile {
    private ProfileDTO data;

    public Profile() {
        this.data = new ProfileDTO();
    }

    public ProfileDTO getData() {
        return data;
    }

    public Profile setData(ProfileDTO data) {
        this.data = data;
        return this;
    }
}
