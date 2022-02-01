package com.authme.authme.data.service;

import com.authme.authme.data.binding.PicturesBindingModel;
import com.authme.authme.data.binding.ProfileBindingModel;
import com.authme.authme.data.binding.RegisterBindingModel;
import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.service.models.RegisterServiceModel;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.AuthenticatedPrincipal;

import javax.persistence.metamodel.SingularAttribute;
import java.io.File;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface AuthMeUserService {
    Optional<AuthMeUserEntity> findByUsername(String username);

    void registerAndLogin(RegisterServiceModel registerServiceModel);

    ProfileBindingModel getProfileBindingModel();

    void patchProfile(ProfileBindingModel profileBindingModel);

    File getPicture(Principal principal, Long pictureId);

    PicturesBindingModel getPicturesBindingModel(Principal principal);

    void init();
}
