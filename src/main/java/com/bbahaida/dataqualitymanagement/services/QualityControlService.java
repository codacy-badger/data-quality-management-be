package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;
import com.bbahaida.dataqualitymanagement.services.checkers.QualityChecker;

import java.util.List;

public interface QualityControlService {
    QualityChecker getQualityChecker();

    void setQualityChecker(QualityChecker qualityChecker);

    QualityCheckingResult checkQuality(List<Object> data);
}
