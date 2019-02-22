package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceIdException;

import java.sql.SQLException;
import java.util.List;

public interface DataSourceService {
    public DBConfiguration setup(final String dbName, final String username, final String password, final String host, final int port, final int type);

    public boolean testConnection(final String dbName, final String username, final String password, final String host, final int port, final int type) throws SQLException;

    public void loadDBConfiguration(final Long id) throws InvalidDataSourceIdException;

    public String getTableName();

    public void setTableName(final String tableName) throws SQLException;

    List<String> getTables()  throws SQLException;

    List<DBConfiguration> getConfigurations();
}
