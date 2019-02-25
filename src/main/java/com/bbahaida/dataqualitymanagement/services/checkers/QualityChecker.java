package com.bbahaida.dataqualitymanagement.services.checkers;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;

import java.util.List;

@FunctionalInterface
public interface QualityChecker {
    QualityCheckingResult check(List<Object> data);
}
