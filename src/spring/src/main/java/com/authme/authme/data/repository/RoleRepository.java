package com.authme.authme.data.repository;

import com.authme.authme.data.entity.Role;
import com.authme.authme.data.entity.enums.AuthMeUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(AuthMeUserRole name);
}
