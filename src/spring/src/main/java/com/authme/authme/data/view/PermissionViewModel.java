package com.authme.authme.data.view;

public class PermissionViewModel {
    private Long id;
    private String fieldName;

    public Long getId() {
        return id;
    }

    public PermissionViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public PermissionViewModel setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }
}
