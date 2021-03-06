package com.authme.authme.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
    @Column(nullable = false)
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public Permission setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Permission))
            return false;
        return ((Permission)obj).getFieldName().equals(this.fieldName);
    }
}
