package com.authme.authme.exceptions;

import javax.persistence.EntityNotFoundException;

public class CommonErrorMessages {
    public static EntityNotFoundException username(String username) {
        return new EntityNotFoundException("User with username " + username + " not found!");
    }

    public static EntityNotFoundException token(String tokenId) {
        return new EntityNotFoundException("Token with id: " + tokenId + " not found!");
    }

    public static EntityNotFoundException permission(Long permissionId) {
        return new EntityNotFoundException("Permission with id: " + permissionId + " not found!");
    }

    public static RuntimeException errorCreatingEntityInSecondService() {
        return new RuntimeException("An error occurred while trying to create entity in second service!");
    }

    public static RuntimeException errorExtractingEntityFromSecondService() {
        return new RuntimeException("An error occurred while trying to extract entity from second service!");
    }
}
