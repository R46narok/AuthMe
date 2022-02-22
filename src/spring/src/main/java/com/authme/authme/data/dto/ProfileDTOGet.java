package com.authme.authme.data.dto;

import com.authme.authme.data.dto.objects.ProfileEntryObject;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class ProfileDTOGet {
    private Long id;
    private ProfileEntryObject<String> name;
    private ProfileEntryObject<String> middleName;
    private ProfileEntryObject<String> surname;
    private ProfileEntryObject<LocalDate> dateOfBirth;

    public ProfileEntryObject<String> getName() {
        return name;
    }

    public ProfileDTOGet setName(ProfileEntryObject<String> name) {
        this.name = name;
        return this;
    }

    public ProfileEntryObject<String> getMiddleName() {
        return middleName;
    }

    public ProfileDTOGet setMiddleName(ProfileEntryObject<String> middleName) {
        this.middleName = middleName;
        return this;
    }

    public ProfileEntryObject<String> getSurname() {
        return surname;
    }

    public ProfileDTOGet setSurname(ProfileEntryObject<String> surname) {
        this.surname = surname;
        return this;
    }

    public ProfileEntryObject<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileDTOGet setDateOfBirth(ProfileEntryObject<LocalDate> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void update(ProfileDTOGet body) {
        Field[] fields = ProfileDTOGet.class.getDeclaredFields();
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

    public Long getId() {
        return id;
    }

    public ProfileDTOGet setId(Long id) {
        this.id = id;
        return this;
    }
}
