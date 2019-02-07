package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.datasources.AppDataSource;
import com.bbahaida.dataqualitymanagement.datasources.AppDataSourceFactory;
import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidTableNameException;
import com.bbahaida.dataqualitymanagement.repositories.DBConfigurationRepository;
import com.bbahaida.dataqualitymanagement.utils.DataSourceConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataSourceServiceImpl implements DataSourceService{


    private String tableName;
    private String sortingColumnName;

    private DriverManagerDataSource clientDataSource;

    private DBConfigurationRepository dbConfigurationRepository;

    private AppDataSourceFactory dataSourceFactory;

    private AccountService accountService;

    public DataSourceServiceImpl(
            @Qualifier(DataSourceConstants.CLIENT_DS_QUALIFIER) DriverManagerDataSource clientDataSource,
            DBConfigurationRepository dbConfigurationRepository,
            AppDataSourceFactory dataSourceFactory,
            AccountService accountService) {
        this.clientDataSource = clientDataSource;
        this.dbConfigurationRepository = dbConfigurationRepository;
        this.dataSourceFactory = dataSourceFactory;
        this.accountService = accountService;
    }

    @Override
    public boolean setup(final String dbName, final String username, final String password, final String host, final int port, final int type) {

        if (dbName != null && username != null && validType(type)) {
            AppDataSource appDataSource = dataSourceFactory.create(type, dbName, host, port);

            configureDataSource(appDataSource.getUrl(), appDataSource.getDriver(), username, password);
            saveDBConfiguration(type, appDataSource.getUrl(), username, password);

            return true;
        }

        throw new InvalidDataSourceTypeException("Invalid data source configuration");
    }


    private boolean validType(final int type) {
        if (type == DataSourceConstants.MYSQL || type == DataSourceConstants.ORACLE || type == DataSourceConstants.POSTGRES){
            return true;
        }
        throw new InvalidDataSourceTypeException("Unsupported data source type");
    }


    private void configureDataSource(final String url, final String driver, final String username, final String password){
        try {
            clientDataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clientDataSource.setUrl(url);
        clientDataSource.setDriverClassName(driver);
        clientDataSource.setUsername(username);
        clientDataSource.setPassword(password);

    }

    private void saveDBConfiguration(final int type, final String url, final String username, final String password) {
        if (dbConfigurationRepository.findDBConfigurationByUrlAndTypeAndUsername(url,type,username) == null){
            DBConfiguration dbConfiguration = new DBConfiguration();
            dbConfiguration.setType(type);
            dbConfiguration.setUrl(url);
            dbConfiguration.setUsername(username);
            dbConfiguration.setPassword(password);
            dbConfiguration.setAppUser(accountService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            dbConfigurationRepository.save(dbConfiguration);
        }
    }
    @Override
    public List<String> getTables() throws SQLException{
        List<String> tables = new ArrayList<>();
        Connection connection = clientDataSource.getConnection();
        ResultSet rs = connection
                .getMetaData()
                .getTables(
                        connection.getCatalog(),
                        connection.getSchema(),
                        "%",
                        null);

        while (rs.next()){
            tables.add(rs.getString(3));
        }

        return tables;
    }


    @Override
    public void changeTable(final String tableName, final String columnName) throws SQLException {
        setTableName(tableName);
        setSortingColumnName(columnName);
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    private void setTableName(final String tableName) throws SQLException {
        if (getTables().contains(tableName)){
            this.tableName = tableName;
        }
        throw new InvalidTableNameException("Table "+tableName+" does not exist");
    }

    @Override
    public String getSortingColumnName() {
        return sortingColumnName;
    }

    private void setSortingColumnName(String sortingColumnName) {
        this.sortingColumnName = sortingColumnName;
    }

    @Override
    public List<DBConfiguration> getConfigurations() {
        return dbConfigurationRepository.findAll();
    }
}
