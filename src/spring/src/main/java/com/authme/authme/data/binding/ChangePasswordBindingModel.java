package com.authme.authme.data.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordBindingModel {
    private String currentPassword;
    @NotNull
    @NotBlank(message = "New password cannot be blank!")
    @Length(min = 8, max = 40, message = "New password length must be between 8 and 40 symbols!")
    private String newPassword;
    @NotNull
    @NotBlank(message = "New password cannot be blank!")
    @Length(min = 8, max = 40, message = "New password length must be between 8 and 40 symbols!")
    private String repeatNewPassword;

    public ChangePasswordBindingModel() {
        this.currentPassword = "";
        this.newPassword = "";
        this.repeatNewPassword = "";
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public ChangePasswordBindingModel setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public ChangePasswordBindingModel setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
        return this;
    }

    public boolean passwordsDontMatch() {
        return !newPassword.equals(repeatNewPassword);
    }
}
