package com.authme.authme.data.binding;

import com.authme.authme.data.validation.annotations.UniqueUsername;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterBindingModel {
    @NotNull
    @NotBlank
    @UniqueUsername
    @Length(min = 4, max = 20)
    private String username;
    @NotNull
    @NotBlank
    @Length(min = 8, max = 40)
    private String password;
    @NotNull
    @NotBlank
    @Length(min = 8, max = 40)
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
