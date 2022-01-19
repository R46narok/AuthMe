package com.authme.authme.data.repository;

import com.authme.authme.data.entity.AuthMeUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthMeUserRepository extends JpaRepository<AuthMeUserEntity, Long> {
    Optional<AuthMeUserEntity> findByUsername(String username);
}
