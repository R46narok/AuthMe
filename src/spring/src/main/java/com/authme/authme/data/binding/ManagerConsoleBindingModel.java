package com.authme.authme.data.binding;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ManagerConsoleBindingModel {
    private Long dataId;
    private String name;
    private String middleName;
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String frontImage;
    private String backImage;

    public String getName() {
        return name;
    }

    public ManagerConsoleBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ManagerConsoleBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ManagerConsoleBindingModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ManagerConsoleBindingModel setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public ManagerConsoleBindingModel setFrontImage(String frontImage) {
        this.frontImage = frontImage;
        return this;
    }

    public String getBackImage() {
        return backImage;
    }

    public ManagerConsoleBindingModel setBackImage(String backImage) {
        this.backImage = backImage;
        return this;
    }

    public Long getDataId() {
        return dataId;
    }

    public ManagerConsoleBindingModel setDataId(Long dataId) {
        this.dataId = dataId;
        return this;
    }
}
