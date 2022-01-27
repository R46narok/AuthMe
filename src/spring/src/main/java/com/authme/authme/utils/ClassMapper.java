package com.authme.authme.utils;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class ClassMapper {
    public static UserDetails toUserDetails(AuthMeUserEntity user) {
        if(user == null)
            return null;
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    public static RegisterServiceModel registerBindingToService(RegisterBindingModel bindingModel) {
        return
                new RegisterServiceModel()
                .setUsername(bindingModel.getUsername())
                .setPassword(bindingModel.getPassword());
    }

    public static ProfileBindingModel toProfileBindingModel(ProfileDTO data) {
        return new ProfileBindingModel()
                .setFirstName(data.getFirstName())
                .setFirstNameValidated(data.isFirstNameValidated())
                .setMiddleName(data.getMiddleName())
                .setMiddleNameValidated(data.isMiddleNameValidated())
                .setLastName(data.getLastName())
                .setLastNameValidated(data.isLastNameValidated())
                .setDateOfBirth(data.getDateOfBirth())
                .setDateOfBirthValidated(data.isDateOfBirthValidated());
    }

    public static ProfileDTO toProfileDTO(ProfileBindingModel profileBindingModel) {
        return new ProfileDTO()
                .setFirstName(profileBindingModel.getFirstName())
                .setFirstNameValidated(profileBindingModel.getFirstNameValidated())
                .setMiddleName(profileBindingModel.getMiddleName())
                .setMiddleNameValidated(profileBindingModel.getMiddleNameValidated())
                .setLastName(profileBindingModel.getLastName())
                .setLastNameValidated(profileBindingModel.getLastNameValidated())
                .setDateOfBirth(profileBindingModel.getDateOfBirth())
                .setDateOfBirthValidated(profileBindingModel.getDateOfBirthValidated());
    }
}
