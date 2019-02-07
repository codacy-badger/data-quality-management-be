package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;

import java.sql.SQLException;
import java.util.List;

public interface DataSourceService {
    public boolean setup(final String dbName, final String username, final String password, final String host, final int port, final int type);

    public String getTableName();
    public String getSortingColumnName();

    public void changeTable(final String tableName, final String columnName) throws SQLException;

    List<String> getTables()  throws SQLException;

    List<DBConfiguration> getConfigurations();
}
