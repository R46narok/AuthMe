package com.authme.authme.data.repository;

import com.authme.authme.data.entity.DataValidationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataValidationRecordRepository extends JpaRepository<DataValidationRecord, Long> {
}
