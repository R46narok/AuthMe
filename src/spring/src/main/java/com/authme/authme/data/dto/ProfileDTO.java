package com.authme.authme.data.dto;

import com.authme.authme.data.entity.enums.Gender;

import java.time.LocalDate;

public class ProfileDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public ProfileDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ProfileDTO setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileDTO setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
