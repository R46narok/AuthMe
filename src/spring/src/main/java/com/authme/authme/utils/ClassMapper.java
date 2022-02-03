package com.authme.authme.utils;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class ClassMapper {
    private final ModelMapper modelMapper;

    public ClassMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDetails toUserDetails(AuthMeUserEntity user) {
        if(user == null)
            return null;
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    public RegisterServiceModel registerBindingToService(RegisterBindingModel bindingModel) {
        return
                new RegisterServiceModel()
                .setUsername(bindingModel.getUsername())
                .setPassword(bindingModel.getPassword());
    }

    public ProfileBindingModel toProfileBindingModel(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileBindingModel.class);
    }

    public ProfileDTO toProfileDTO(ProfileBindingModel profileBindingModel) {
        return modelMapper.map(profileBindingModel, ProfileDTO.class);
    }
}
