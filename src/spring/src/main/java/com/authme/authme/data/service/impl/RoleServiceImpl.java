package com.authme.authme.data.service.impl;

import com.authme.authme.data.entity.Role;
import com.authme.authme.data.entity.enums.AuthMeUserRole;
import com.authme.authme.data.repository.RoleRepository;
import com.authme.authme.data.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void init() {
        if (roleRepository.count() == 0) {
            Arrays.stream(AuthMeUserRole.values()).forEach(r -> {
                Role role = new Role()
                        .setName(r);
                roleRepository.saveAndFlush(role);
            });
        }
    }
}
