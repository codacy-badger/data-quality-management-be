package com.bbahaida.dataqualitymanagement.services.checkers;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;
import com.bbahaida.dataqualitymanagement.exceptions.DataNotFoundException;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class NotNullQualityCheckerTest {

    private QualityChecker checker = new NotNullQualityChecker();
    private List<Object> data;
    private static final double ERR_SCORE = 0.0001;

    @Before
    public void setup() {
        this.data = Lists.newArrayList(null, 1, null, 3, 4);

    }

    @Test
    public void check_ShouldReturnIntanceOfQualityCheckingResult() throws Exception {
        assertThat(checker.check(data), instanceOf(QualityCheckingResult.class));
    }

    @Test(expected = DataNotFoundException.class)
    public void check_ShouldThrowDataNotFoundExceptionIfDataIsNull() throws Exception {
        checker.check(null);
    }

    @Test
    public void check_ShouldReturnIntanceOfQualityCheckingResultWithScore() throws Exception {
        QualityCheckingResult result = checker.check(data);
        assertThat(result, hasProperty("score", closeTo(0.6, ERR_SCORE)));
    }
}
