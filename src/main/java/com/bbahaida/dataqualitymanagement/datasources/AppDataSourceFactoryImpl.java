package com.bbahaida.dataqualitymanagement.datasources;

import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;
import com.bbahaida.dataqualitymanagement.utils.DataSourceConstants;

public class AppDataSourceFactoryImpl implements AppDataSourceFactory {
    @Override
    public AppDataSource create(final int type, final String dbName, final String host, final int port) throws InvalidDataSourceTypeException {
        switch (type){
            case DataSourceConstants.MYSQL: return new MySqlDataSource(dbName, host, port);
            case DataSourceConstants.POSTGRES: return new PostgresDataSource(dbName, host, port);
            default: throw new InvalidDataSourceTypeException("RDBMS not supported yet");
        }
    }
}
