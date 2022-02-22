package com.authme.authme.utils;

import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.dto.ProfileDTOGet;
import com.authme.authme.data.dto.ProfileDTOPost;
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

    public ProfileBindingModel toProfileBindingModel(ProfileDTOGet profileDTOGet) {
        return new ProfileBindingModel()
                .setFirstName(profileDTOGet.getName().getValue())
                .setFirstNameValidated(profileDTOGet.getName().getValidated())
                .setMiddleName(profileDTOGet.getMiddleName().getValue())
                .setMiddleNameValidated(profileDTOGet.getMiddleName().getValidated())
                .setLastName(profileDTOGet.getSurname().getValue())
                .setLastNameValidated(profileDTOGet.getSurname().getValidated())
                .setDateOfBirth(profileDTOGet.getDateOfBirth().getValue())
                .setDateOfBirthValidated(profileDTOGet.getDateOfBirth().getValidated());
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

    public ProfileDTOPost toProfileDTOSend(ProfileBindingModel profileBindingModel) {
        return new ProfileDTOPost()
                .setName(profileBindingModel.getFirstName())
                .setMiddleName(profileBindingModel.getMiddleName())
                .setSurname(profileBindingModel.getLastName())
                .setDateOfBirth(profileBindingModel.getDateOfBirth());
    }
}
