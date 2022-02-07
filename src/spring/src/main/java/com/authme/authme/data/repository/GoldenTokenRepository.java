package com.authme.authme.data.repository;

import com.authme.authme.data.entity.GoldenToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldenTokenRepository extends JpaRepository<GoldenToken, String> {

}
