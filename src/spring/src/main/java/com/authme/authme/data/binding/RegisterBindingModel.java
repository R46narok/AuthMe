package com.authme.authme.data.binding;

import com.authme.authme.data.validation.annotations.UniqueUsername;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterBindingModel {
    @NotNull
    @NotBlank(message = "Username cannot be blank!")
    @UniqueUsername(message = "This username already exists!")
    @Length(min = 4, max = 20, message = "Username length must be between 4 and 20 symbols!")
    private String username;
    @NotNull
    @NotBlank(message = "Password cannot be blank!")
    @Length(min = 8, max = 40, message = "Password length must be between 8 and 40 symbols!")
    private String password;
    @NotNull
    @NotBlank(message = "Repeated password cannot be blank!")
    @Length(min = 8, max = 40, message = "Repeated password length must be between 8 and 40 symbols!")
    private String repeatPassword;

    public boolean passwordsDontMatch() {
        return !password.equals(repeatPassword);
    }

    public String getUsername() {
        return username;
    }

    public RegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public RegisterBindingModel setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }
}
