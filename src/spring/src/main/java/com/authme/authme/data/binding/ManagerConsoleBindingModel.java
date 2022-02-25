package com.authme.authme.data.binding;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ManagerConsoleBindingModel {
    private Long identityId;
    private String name;
    private String middleName;
    private String surname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String frontImage;
    private String backImage;

    public Long getIdentityId() {
        return identityId;
    }

    public ManagerConsoleBindingModel setIdentityId(Long identityId) {
        this.identityId = identityId;
        return this;
    }

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
}
