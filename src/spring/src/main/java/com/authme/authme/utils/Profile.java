package com.authme.authme.utils;

import com.authme.authme.data.dto.ProfileDTOGet;

public class Profile {
    private ProfileDTOGet data;

    public Profile() {
        this.data = new ProfileDTOGet();
    }

    public ProfileDTOGet getData() {
        return data;
    }

    public Profile setData(ProfileDTOGet data) {
        this.data = data;
        return this;
    }
}
