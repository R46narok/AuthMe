package com.authme.authme.data.dto.objects;

import java.time.LocalDateTime;

public class ProfileEntryObject<T> {
    private T value;
    private Boolean validated;
    private LocalDateTime lastUpdated;

    public ProfileEntryObject() {
        validated = true;
    }

    public T getValue() {
        return value;
    }

    public ProfileEntryObject<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public Boolean getValidated() {
        return validated;
    }

    public ProfileEntryObject<T> setValidated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public ProfileEntryObject<T> setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }
}
