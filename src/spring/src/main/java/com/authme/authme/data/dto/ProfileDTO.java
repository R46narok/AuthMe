package com.authme.authme.data.dto;

import java.time.LocalDate;

public class ProfileDTO {
    private String firstName;
    private boolean firstNameValidated;
    private String middleName;
    private boolean middleNameValidated;
    private String lastName;
    private boolean lastNameValidated;
    private LocalDate dateOfBirth;
    private boolean dateOfBirthValidated;

    public ProfileDTO() {
        firstName = "";
        firstNameValidated = true;
        middleName = "";
        middleNameValidated = true;
        lastName = "";
        lastNameValidated = true;
        dateOfBirth = null;
        dateOfBirthValidated = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public ProfileDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public boolean isFirstNameValidated() {
        return firstNameValidated;
    }

    public ProfileDTO setFirstNameValidated(boolean firstNameValidated) {
        this.firstNameValidated = firstNameValidated;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ProfileDTO setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public boolean isMiddleNameValidated() {
        return middleNameValidated;
    }

    public ProfileDTO setMiddleNameValidated(boolean middleNameValidated) {
        this.middleNameValidated = middleNameValidated;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isLastNameValidated() {
        return lastNameValidated;
    }

    public ProfileDTO setLastNameValidated(boolean lastNameValidated) {
        this.lastNameValidated = lastNameValidated;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileDTO setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public boolean isDateOfBirthValidated() {
        return dateOfBirthValidated;
    }

    public ProfileDTO setDateOfBirthValidated(boolean dateOfBirthValidated) {
        this.dateOfBirthValidated = dateOfBirthValidated;
        return this;
    }
}
