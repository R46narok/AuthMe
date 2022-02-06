package com.authme.authme.data.dto;

import com.authme.authme.data.dto.objects.ProfileEntryObject;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class ProfileDTO {
    private ProfileEntryObject<String> firstName;
    private ProfileEntryObject<String> middleName;
    private ProfileEntryObject<String> lastName;
    private ProfileEntryObject<LocalDate> dateOfBirth;

    public ProfileDTO() {
        this.firstName = new ProfileEntryObject<>();
        this.middleName = new ProfileEntryObject<>();
        this.lastName = new ProfileEntryObject<>();
        this.dateOfBirth = new ProfileEntryObject<>();
    }

    public ProfileEntryObject<String> getFirstName() {
        return firstName;
    }

    public ProfileDTO setFirstName(ProfileEntryObject<String> firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfileEntryObject<String> getMiddleName() {
        return middleName;
    }

    public ProfileDTO setMiddleName(ProfileEntryObject<String> middleName) {
        this.middleName = middleName;
        return this;
    }

    public ProfileEntryObject<String> getLastName() {
        return lastName;
    }

    public ProfileDTO setLastName(ProfileEntryObject<String> lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileEntryObject<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileDTO setDateOfBirth(ProfileEntryObject<LocalDate> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void update(ProfileDTO body) {
        Field[] fields = ProfileDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                ProfileEntryObject<Object> currentField = (ProfileEntryObject<Object>) field.get(this);
                ProfileEntryObject<Object> newField = (ProfileEntryObject<Object>) field.get(body);
                if(newField != null && !newField.getValue().equals(currentField)) {
                    currentField.setValue(newField.getValue());
                    currentField.setValidated(false);
                }
                else if(newField == null) {
                    currentField.setValue(newField.getValue());
                    currentField.setValidated(true);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
