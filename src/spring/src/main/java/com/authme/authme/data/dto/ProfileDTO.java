package com.authme.authme.data.dto;

import com.authme.authme.data.dto.objects.ProfileEntryObject;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class ProfileDTO {
    private ProfileEntryObject<String> name;
    private ProfileEntryObject<String> middleName;
    private ProfileEntryObject<String> surname;
    private ProfileEntryObject<LocalDate> dateOfBirth;

    public ProfileEntryObject<String> getName() {
        return name;
    }

    public ProfileDTO setName(ProfileEntryObject<String> name) {
        this.name = name;
        return this;
    }

    public ProfileEntryObject<String> getMiddleName() {
        return middleName;
    }

    public ProfileDTO setMiddleName(ProfileEntryObject<String> middleName) {
        this.middleName = middleName;
        return this;
    }

    public ProfileEntryObject<String> getSurname() {
        return surname;
    }

    public ProfileDTO setSurname(ProfileEntryObject<String> surname) {
        this.surname = surname;
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
