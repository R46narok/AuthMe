package com.authme.authme.data.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ValidateProfileBindingModel {
    private String name;
    private String middleName;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    public String getName() {
        return name;
    }

    public ValidateProfileBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ValidateProfileBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ValidateProfileBindingModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ValidateProfileBindingModel setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
