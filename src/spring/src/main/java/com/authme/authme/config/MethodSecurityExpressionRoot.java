package com.authme.authme.config;

import com.authme.authme.data.entity.enums.AuthMeUserRole;
import com.authme.authme.data.service.CurrentUserService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import javax.transaction.Transactional;

public class MethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {
    private Object filterObject, returnObject;
    private final CurrentUserService currentUserService;

    public MethodSecurityExpressionRoot(Authentication authentication, CurrentUserService currentUserService) {
        super(authentication);
        this.currentUserService = currentUserService;
    }

    public boolean isManager() {
        return currentUserService.getCurrentLoggedUser().getRoles().stream().anyMatch(r -> r.getName().equals(AuthMeUserRole.MANAGER));
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
