package com.authme.authme.data.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ProfileBindingModel {
    private String firstName;
    private Boolean firstNameValidated;
    private String lastName;
    private Boolean lastNameValidated;
    private String middleName;
    private Boolean middleNameValidated;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Boolean dateOfBirthValidated;

    public String getFirstName() {
        return firstName;
    }

    public ProfileBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Boolean getFirstNameValidated() {
        return firstNameValidated;
    }

    public ProfileBindingModel setFirstNameValidated(Boolean firstNameValidated) {
        this.firstNameValidated = firstNameValidated;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Boolean getLastNameValidated() {
        return lastNameValidated;
    }

    public ProfileBindingModel setLastNameValidated(Boolean lastNameValidated) {
        this.lastNameValidated = lastNameValidated;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ProfileBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public Boolean getMiddleNameValidated() {
        return middleNameValidated;
    }

    public ProfileBindingModel setMiddleNameValidated(Boolean middleNameValidated) {
        this.middleNameValidated = middleNameValidated;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileBindingModel setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public Boolean getDateOfBirthValidated() {
        return dateOfBirthValidated;
    }

    public ProfileBindingModel setDateOfBirthValidated(Boolean dateOfBirthValidated) {
        this.dateOfBirthValidated = dateOfBirthValidated;
        return this;
    }
}
