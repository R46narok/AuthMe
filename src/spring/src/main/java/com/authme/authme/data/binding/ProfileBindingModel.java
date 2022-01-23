package com.authme.authme.data.binding;

import com.authme.authme.data.entity.enums.Gender;

import java.time.LocalDate;

public class ProfileBindingModel {
    private String name;
    private String middleName;
    private String surname;
    private String personalNumber;
    private Gender gender;
    private String nationality;
    private LocalDate dateOfBirth;
    private LocalDate idCardDateOfExpiry;
    private String idCardNumber;
    private String placeOfBirth;
    private String residence;
    private Integer height;
    private String eyeColor;
    private String authority;
    private LocalDate idCardIssueDate;
    private String idCardFrontImageId;
    private String idCardBackImageId;
    private String imageId;

    public String getName() {
        return name;
    }

    public ProfileBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ProfileBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public ProfileBindingModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public ProfileBindingModel setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public ProfileBindingModel setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public ProfileBindingModel setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ProfileBindingModel setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public LocalDate getIdCardDateOfExpiry() {
        return idCardDateOfExpiry;
    }

    public ProfileBindingModel setIdCardDateOfExpiry(LocalDate idCardDateOfExpiry) {
        this.idCardDateOfExpiry = idCardDateOfExpiry;
        return this;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public ProfileBindingModel setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public ProfileBindingModel setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public String getResidence() {
        return residence;
    }

    public ProfileBindingModel setResidence(String residence) {
        this.residence = residence;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public ProfileBindingModel setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public ProfileBindingModel setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    public ProfileBindingModel setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public LocalDate getIdCardIssueDate() {
        return idCardIssueDate;
    }

    public ProfileBindingModel setIdCardIssueDate(LocalDate idCardIssueDate) {
        this.idCardIssueDate = idCardIssueDate;
        return this;
    }

    public String getIdCardFrontImageId() {
        return idCardFrontImageId;
    }

    public ProfileBindingModel setIdCardFrontImageId(String idCardFrontImageId) {
        this.idCardFrontImageId = idCardFrontImageId;
        return this;
    }

    public String getIdCardBackImageId() {
        return idCardBackImageId;
    }

    public ProfileBindingModel setIdCardBackImageId(String idCardBackImageId) {
        this.idCardBackImageId = idCardBackImageId;
        return this;
    }

    public String getImageId() {
        return imageId;
    }

    public ProfileBindingModel setImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }
}
