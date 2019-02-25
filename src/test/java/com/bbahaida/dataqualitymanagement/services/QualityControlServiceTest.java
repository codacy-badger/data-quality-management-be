package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.QualityCheckingResult;
import com.bbahaida.dataqualitymanagement.exceptions.CheckingDataException;
import com.bbahaida.dataqualitymanagement.exceptions.DataNotFoundException;
import com.bbahaida.dataqualitymanagement.exceptions.QualityCheckerNotFoundException;
import com.bbahaida.dataqualitymanagement.exceptions.RepositoryNotFoundException;
import com.bbahaida.dataqualitymanagement.repositories.QualityCheckingResultRepository;
import com.bbahaida.dataqualitymanagement.services.checkers.NotNullQualityChecker;
import com.bbahaida.dataqualitymanagement.services.checkers.QualityChecker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QualityControlServiceTest {

    @Mock
    private QualityCheckingResultRepository resultRepository;
    private List<Object> data;

    @InjectMocks
    private QualityControlService service = new QualityControlServiceImpl(resultRepository);

    @Before
    public void setup() {
        data = new ArrayList<>();
        data.add(1);
        data.add(3);
        service.setQualityChecker(new NotNullQualityChecker());
    }

    @Test
    public void getQualityChecker_ShouldReturnInstanceOfQualityChecker() throws Exception {
        assertThat(service.getQualityChecker(), instanceOf(QualityChecker.class));
    }

    @Test(expected = QualityCheckerNotFoundException.class)
    public void checkQuality_ShouldThrowQualityCheckerNotFoundExceptionIfQualityCheckerIsNull() throws Exception {
        service.setQualityChecker(null);
        service.checkQuality(data);
    }

    @Test(expected = DataNotFoundException.class)
    public void checkQuality_ShouldThrowDataNotFoundExceptionIfDataIsNull() throws Exception {
        service.checkQuality(null);
    }

    @Test(expected = RepositoryNotFoundException.class)
    public void checkQuality_ShouldThrowRepositoryNotFoundExceptionIfRepositoryIsNull() throws Exception {
        service = new QualityControlServiceImpl(null);
        service.checkQuality(data);
    }

    @Test(expected = CheckingDataException.class)
    public void checkQuality_ShouldThrowCheckingDataExceptionIfDataIsEmpty() throws Exception {
        data = new ArrayList<>();
        service.checkQuality(data);
    }

    @Test
    public void checkQuality_ShouldReturnInstanceOfQualityCheckingResult() throws Exception {
        QualityCheckingResult result = new QualityCheckingResult();
        result.setScore(1.0);
        when(resultRepository.save(result)).thenReturn(result);
        assertThat(service.checkQuality(data), instanceOf(QualityCheckingResult.class));
        verify(resultRepository).save(result);
    }

    /*@Test
    public void testTypes() throws Exception{
        Object i = 1;
        String o = i.getClass().getTypeName();
        assertThat(Integer.class, equalTo(Class.forName(o)));
    }*/


}
