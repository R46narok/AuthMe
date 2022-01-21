package com.authme.authme.data.validation.validators;

import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.validation.annotations.UniqueUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final AuthMeUserRepository userRepository;

    public UniqueUsernameValidator(AuthMeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)
            return true;
        return userRepository.findByUsername(value).isEmpty();
    }
}
