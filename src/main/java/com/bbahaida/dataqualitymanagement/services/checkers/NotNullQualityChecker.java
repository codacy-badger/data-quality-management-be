package com.bbahaida.dataqualitymanagement.services.checkers;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;
import com.bbahaida.dataqualitymanagement.exceptions.DataNotFoundException;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NotNullQualityChecker implements QualityChecker {
    @Override
    public QualityCheckingResult check(List<Object> data) {
        if (data == null) {
            throw new DataNotFoundException("Data should not be null");
        }
        Set<Object> newData = Sets.newHashSet(Iterables.filter(data, Objects::nonNull));
        double score = (double) newData.size() / (double) data.size();
        QualityCheckingResult result = new QualityCheckingResult();
        result.setScore(score);
        return result;
    }
}
