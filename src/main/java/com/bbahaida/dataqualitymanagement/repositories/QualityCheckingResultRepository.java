package com.bbahaida.dataqualitymanagement.repositories;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualityCheckingResultRepository extends JpaRepository<QualityCheckingResult, Long> {
}
