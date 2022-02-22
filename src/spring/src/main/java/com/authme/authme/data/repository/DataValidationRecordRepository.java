package com.authme.authme.data.repository;

import com.authme.authme.data.entity.DataValidationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataValidationRecordRepository extends JpaRepository<DataValidationRecord, Long> {
    Optional<DataValidationRecord> findByPlatinumToken(String platinumToken);

    List<DataValidationRecord> findByUserIdOrderByIdDesc(Long id);
}
