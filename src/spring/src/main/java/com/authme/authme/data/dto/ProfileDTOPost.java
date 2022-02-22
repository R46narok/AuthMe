package com.authme.authme.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ProfileDTOPost {
    private Long id;
    private String name;
    private String middleName;
    private String surname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    public Long getId() {
        return id;
    }

    public ProfileDTOPost setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProfileDTOPost setName(String name) {
        this.name = name;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ProfileDTOPost setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ProfileDTOPost setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileDTOPost setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
