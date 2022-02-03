package com.authme.authme.data.binding;

import com.authme.authme.data.dto.objects.ProfileEntryObject;

import java.time.LocalDate;

public class ProfileBindingModel {
    private ProfileEntryObject<String> firstName;
    private ProfileEntryObject<String> middleName;
    private ProfileEntryObject<String> lastName;
    private ProfileEntryObject<LocalDate> dateOfBirth;

    public ProfileEntryObject<String> getFirstName() {
        return firstName;
    }

    public ProfileBindingModel setFirstName(ProfileEntryObject<String> firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfileEntryObject<String> getMiddleName() {
        return middleName;
    }

    public ProfileBindingModel setMiddleName(ProfileEntryObject<String> middleName) {
        this.middleName = middleName;
        return this;
    }

    public ProfileEntryObject<String> getLastName() {
        return lastName;
    }

    public ProfileBindingModel setLastName(ProfileEntryObject<String> lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileEntryObject<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileBindingModel setDateOfBirth(ProfileEntryObject<LocalDate> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
