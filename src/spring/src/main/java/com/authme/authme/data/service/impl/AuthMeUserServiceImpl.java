package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.AuthMeUserEntity;
import com.authme.authme.data.entity.DataValidationRecord;
import com.authme.authme.data.entity.GoldenToken;
import com.authme.authme.data.entity.enums.AuthMeUserRole;
import com.authme.authme.data.repository.AuthMeUserRepository;
import com.authme.authme.data.repository.RoleRepository;
import com.authme.authme.data.service.*;
import com.authme.authme.data.service.models.RegisterServiceModel;
import com.authme.authme.data.view.DataMonitorViewModel;
import com.authme.authme.data.view.GoldenTokenView;
import com.authme.authme.utils.ClassMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthMeUserServiceImpl implements AuthMeUserService {
    private final AuthMeUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PersonalDataService personalDataService;
    private final CurrentUserService currentUserService;
    private final GoldenTokenService goldenTokenService;
    private final ClassMapper classMapper;
    private final PermissionService permissionService;

    public AuthMeUserServiceImpl(AuthMeUserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 UserDetailsServiceImpl userDetailsService,
                                 PersonalDataService personalDataService,
                                 CurrentUserService currentUserService,
                                 GoldenTokenService goldenTokenService,
                                 ClassMapper classMapper, PermissionService permissionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.personalDataService = personalDataService;
        this.currentUserService = currentUserService;
        this.goldenTokenService = goldenTokenService;
        this.classMapper = classMapper;
        this.permissionService = permissionService;
    }

    @Override
    public void registerAndLogin(RegisterServiceModel registerServiceModel) {
        AuthMeUserEntity user = new AuthMeUserEntity()
                .setUsername(registerServiceModel.getUsername())
                .setPassword(passwordEncoder.encode(registerServiceModel.getPassword()))
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(personalDataService.newEntry());
        user = userRepository.saveAndFlush(user);

        if (user != null) {
            UserDetails principal = this.userDetailsService.loadUserByUsername(user.getUsername());
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            user.getPassword(),
                            principal.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }

    @Override
    public DataMonitorViewModel getDataMonitorViewModel() {
        List<DataValidationRecord> records = currentUserService.getCurrentLoggedUser().getValidationRecords();
        return classMapper.toDataMonitorViewModel(records);
    }

    @Override
    public String generateGoldenToken() {
        AuthMeUserEntity user = currentUserService.getCurrentLoggedUser();
        return goldenTokenService.generateFor(user);
    }

    @Override
    public List<GoldenTokenView> getCurrentUserGoldenTokens() {
        AuthMeUserEntity user = currentUserService.getCurrentLoggedUserOrNull();
        if (user == null)
            return new ArrayList<>();
        if (user.getGoldenTokens() != null && user.getGoldenTokens().stream().anyMatch(t -> t.getExpiry().isAfter(LocalDateTime.now()))) {
            return classMapper.toGoldenTokenViewList(
                    user.getGoldenTokens()
                            .stream()
                            .filter(t -> t.getExpiry().isAfter(LocalDateTime.now()))
                            .sorted(Comparator.comparing(GoldenToken::getExpiry))
                            .collect(Collectors.toList()),
                    permissionService.getAll());
        }
        return new ArrayList<>();
    }

    @Override
    public boolean tokenBelongsToCurrentUser(String goldenToken) {
        return currentUserService.getCurrentLoggedUser().getGoldenTokens().stream().anyMatch(t -> t.getId().equals(goldenToken));
    }

    @Override
    public void init() {
        AuthMeUserEntity user = new AuthMeUserEntity()
                .setUsername("test")
                .setPassword("8abb9bfc12232d7e0ca435b875173f577729923491d7e12a144fd26970b30060d7c8b04484e130e7")
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(personalDataService.newEntry());
        AuthMeUserEntity mitko = new AuthMeUserEntity()
                .setUsername("mitko")
                .setPassword("addc1ad2644907e0422ccdb79538de69cd4daf72ab5e0a26e85ed8ceb5208b47101fb93f7520dcc4")
                .setRoles(List.of(roleRepository.findByName(AuthMeUserRole.USER).get()))
                .setDataId(personalDataService.newEntry());

        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(mitko);
    }
}
