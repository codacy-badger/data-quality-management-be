package com.bbahaida.dataqualitymanagement.datasources;

import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;

@FunctionalInterface
public interface AppDataSourceFactory {
    AppDataSource create(final int type, final String dbName, final String host, final int port) throws InvalidDataSourceTypeException;
}
