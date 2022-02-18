package com.authme.authme.data.dto;

import java.util.List;

public class ValidatableResponse<T> {
    private Boolean valid;
    private List<String> errors;
    private T result;

    public boolean isValid() {
        return valid;
    }

    public ValidatableResponse<T> setValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ValidatableResponse<T> setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ValidatableResponse<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
