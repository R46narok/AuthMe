package com.authme.authme.utils;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.dto.objects.ProfileEntryObject;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class ClassMapper extends ModelMapper {

    public UserDetails toUserDetails(AuthMeUserEntity user) {
        if (user == null)
            return null;
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    public RegisterServiceModel registerBindingToService(RegisterBindingModel bindingModel) {
        return super.map(bindingModel, RegisterServiceModel.class);
    }

    public ProfileBindingModel toProfileBindingModel(ProfileDTO profileDTO) {
        return new ProfileBindingModel()
                .setFirstName(profileDTO.getFirstName().getValue())
                .setFirstNameValidated(profileDTO.getFirstName().getValidated())
                .setMiddleName(profileDTO.getMiddleName().getValue())
                .setMiddleNameValidated(profileDTO.getMiddleName().getValidated())
                .setLastName(profileDTO.getLastName().getValue())
                .setLastNameValidated(profileDTO.getLastName().getValidated())
                .setDateOfBirth(profileDTO.getDateOfBirth().getValue())
                .setDateOfBirthValidated(profileDTO.getDateOfBirth().getValidated());
    }

    public ProfileDTO toProfileDTO(ProfileBindingModel profileBindingModel) {
        return new ProfileDTO()
                .setFirstName(new ProfileEntryObject<String>().setValue(profileBindingModel.getFirstName()).setValidated(profileBindingModel.getFirstNameValidated()))
                .setMiddleName(new ProfileEntryObject<String>().setValue(profileBindingModel.getMiddleName()).setValidated(profileBindingModel.getMiddleNameValidated()))
                .setLastName(new ProfileEntryObject<String>().setValue(profileBindingModel.getLastName()).setValidated(profileBindingModel.getLastNameValidated()))
                .setDateOfBirth(new ProfileEntryObject<LocalDate>().setValue(profileBindingModel.getDateOfBirth()).setValidated(profileBindingModel.getDateOfBirthValidated()));
    }
}
