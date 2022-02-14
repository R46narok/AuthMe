package com.authme.authme.utils;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.dto.ProfileDTO;
import com.authme.authme.data.dto.objects.ProfileEntryObject;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.Permission;
import com.authme.authme.data.service.models.RegisterServiceModel;
import com.authme.authme.data.view.DataAccessRequestRecordViewModel;
import com.authme.authme.data.view.DataMonitorViewModel;
import com.authme.authme.data.view.GoldenTokenView;
import com.authme.authme.data.view.PermissionViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    public List<PermissionViewModel> toPermissionViewModelList(List<Permission> all) {
        List<PermissionViewModel> permissions = new ArrayList<>();
        for (Permission permission : all) {
            permissions.add(super.map(permission, PermissionViewModel.class));
        }
        return permissions;
    }

    public DataAccessRequestRecordViewModel toDataAccessRequestRecordViewModel(DataValidationRecord validationRecord) {
        return super.map(validationRecord, DataAccessRequestRecordViewModel.class);
    }

    public DataMonitorViewModel toDataMonitorViewModel(List<DataValidationRecord> records) {
        DataMonitorViewModel viewModel = new DataMonitorViewModel();
        for (DataValidationRecord record : records) {
            viewModel.getRequests().add(toDataAccessRequestRecordViewModel(record));
        }
        return viewModel;
    }

    public List<GoldenTokenView> toGoldenTokenViewList(List<GoldenToken> tokens, List<Permission> allPermissions) {
        List<GoldenTokenView> goldenTokenViews = new ArrayList<>();
        for (GoldenToken token : tokens) {
            goldenTokenViews.add(toGoldenTokenView(token, allPermissions));
        }
        return goldenTokenViews;
    }

    public GoldenTokenView toGoldenTokenView(GoldenToken token, List<Permission> allPermissions) {
        List<PermissionViewModel> allPermissionViews = new ArrayList<>();
        for (Permission permission : allPermissions) {
            allPermissionViews.add(
                    new PermissionViewModel()
                            .setId(permission.getId())
                            .setFieldName(permission.getFieldName())
                            .setAllowed(false)
            );
        }

        for (Permission permission : token.getPermissions()) {
            allPermissionViews
                    .stream()
                    .filter(p -> p.getId().equals(permission.getId()))
                    .findFirst()
                    .get()
                    .setAllowed(true);
        }

        GoldenTokenView goldenTokenView = new GoldenTokenView()
                .setId(token.getId())
                .setExpiry(token.getExpiry())
                .setPermissions(allPermissionViews);
        return goldenTokenView;
    }
}
