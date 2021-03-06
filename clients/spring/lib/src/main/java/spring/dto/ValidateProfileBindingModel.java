package spring.dto;

import java.time.LocalDate;

public class ValidateProfileBindingModel {
    private String name;
    private String middleName;
    private String surname;
    private String dateOfBirth;

    public ValidateProfileBindingModel() {
        name = "";
        middleName = "";
        surname = "";
        dateOfBirth = null;
    }

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public ValidateProfileBindingModel setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
