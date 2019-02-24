package com.bbahaida.dataqualitymanagement.datasources;

import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppDataSourceFactoryTest {

    AppDataSourceFactory sourceFactory;

    @Before
    public void before() {
        sourceFactory = new AppDataSourceFactoryImpl();
    }

    @Test
    public void should_ReturnMysqlDriver_IfTypeIs1() {
        // arrange
        String expected = "com.mysql.jdbc.Driver";
        AppDataSource source = sourceFactory.create(1, "test", "test", 0);
        // act
        String actual = source.getDriver();
        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnPostgresDriver_IfTypeIs2() {
        // arrange
        String expected = "org.postgresql.Driver";
        AppDataSource source = sourceFactory.create(2, "test", "test", 0);
        // act
        String actual = source.getDriver();
        // assert
        assertEquals(expected, actual);
    }

    @Test
    public void should_CreateMysqlDataSource_IfTypeIs1() {
        // arrange
        AppDataSource source = sourceFactory.create(1, "test", "test", 0);
        // act
        // assert
        assertTrue(source instanceof MySqlDataSource);
    }

    @Test
    public void should_CreatePostgresDataSource_IfTypeIs2() {
        // arrange
        AppDataSource source = sourceFactory.create(2, "test", "test", 0);
        // act
        // assert
        assertTrue(source instanceof PostgresDataSource);
    }

    @Test
    public void should_UrlContainsMysqlDbNameHostPort_IfTypeIs1() {
        // arrange
        String expected = "jdbc:mysql://test:0/test?serverTimezone=UTC";
        AppDataSource source = sourceFactory.create(1, "test", "test", 0);
        // act
        String actual = source.getUrl();
        // assert
        assertEquals(expected, actual);

    }

    @Test
    public void should_UrlContainsPostgresDbNameHostPort_IfTypeIs2() {
        // arrange
        String expected = "jdbc:postgresql://test:0/test";
        AppDataSource source = sourceFactory.create(2, "test", "test", 0);
        // act
        String actual = source.getUrl();
        // assert
        assertEquals(expected, actual);

    }

    @Test
    public void should_ThrowsInvalidDataSourceTypeException_IfTypeIs0() {
        try {
            AppDataSource source = sourceFactory.create(0, "test", "test", 0);
            fail("Should throw an InvalidDataSourceTypeException");
        } catch (InvalidDataSourceTypeException e) {
            assertTrue(e.getMessage().contains("RDBMS not supported yet"));
        }

    }


}
