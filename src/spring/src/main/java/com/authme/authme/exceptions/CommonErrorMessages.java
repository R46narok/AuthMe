package com.authme.authme.exceptions;

import javax.persistence.EntityNotFoundException;

public class CommonErrorMessages {
    public static EntityNotFoundException username(String username) {
        return new EntityNotFoundException("User with username " + username + " not found!");
    }
}
