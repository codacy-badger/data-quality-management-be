package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;
import com.bbahaida.dataqualitymanagement.exceptions.*;
import com.bbahaida.dataqualitymanagement.repositories.QualityCheckingResultRepository;
import com.bbahaida.dataqualitymanagement.services.checkers.QualityChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QualityControlServiceImpl implements QualityControlService {

    private QualityCheckingResultRepository resultRepository;

    public QualityControlServiceImpl(QualityCheckingResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    private QualityChecker qualityChecker;

    @Override
    public QualityChecker getQualityChecker() {
        return qualityChecker;
    }

    @Override
    public void setQualityChecker(QualityChecker qualityChecker) {
        this.qualityChecker = qualityChecker;
    }

    @Override
    public QualityCheckingResult checkQuality(List<Object> data) {
        if (isValid(data)) {
            QualityCheckingResult checkingResult = qualityChecker.check(data);
            resultRepository.save(checkingResult);
            return checkingResult;
        }
        throw new UnexpectedException("Unexpected error");
    }

    private boolean isValid(List<Object> data) {
        if (resultRepository == null) {
            throw new RepositoryNotFoundException("Result Repository should not be null");
        }
        if (this.qualityChecker == null) {
            throw new QualityCheckerNotFoundException("Quality Checker should not be null");
        }
        if (data == null) {
            throw new DataNotFoundException("Data should not be null");
        }
        if (data.size() == 0) {
            throw new CheckingDataException("Data should not be empty");
        }

        return true;
    }
}
