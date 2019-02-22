package com.bbahaida.dataqualitymanagement.services;

import com.bbahaida.dataqualitymanagement.datasources.AppDataSource;
import com.bbahaida.dataqualitymanagement.datasources.AppDataSourceFactory;
import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import com.bbahaida.dataqualitymanagement.exceptions.DataSourceExistsException;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidTableNameException;
import com.bbahaida.dataqualitymanagement.repositories.DBConfigurationRepository;
import com.bbahaida.dataqualitymanagement.utils.DataSourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(DataSourceServiceImpl.class);


    private String tableName;

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
    public DBConfiguration setup(final String dbName, final String username, final String password, final String host, final int port, final int type) {

        try {
            if (dbName != null && username != null && validType(type)) {
                AppDataSource appDataSource = dataSourceFactory.create(type, dbName, host, port);

                configureDataSource(appDataSource.getUrl(), appDataSource.getDriver(), username, password);
                return saveDBConfiguration(dbName, username, password, host, port, type);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        throw new InvalidDataSourceTypeException("Invalid data source configuration");
    }


    private boolean validType(final int type) throws IllegalAccessException {

        // TODO verifying types dynamically
        /*Field [] fields = DataSourceConstants.class.getFields();
        for (Field field: fields){
            field.setAccessible(true);
            Integer value = (Integer) field.get(DataSourceConstants.class);
            logger.info("By reflection "+value.toString());
        }*/

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

    private DBConfiguration saveDBConfiguration(final String dbName, final String username, final String password, final String host, final int port, final int type) {
        if (dbConfigurationRepository
                .findDBConfigurationByDatabaseNameAndUsernameAndPasswordAndHostAndPortAndType(dbName, username, password, host, port, type) == null) {
            DBConfiguration dbConfiguration = new DBConfiguration();
            dbConfiguration.setType(type);
            dbConfiguration.setHost(host);
            dbConfiguration.setPort(port);
            dbConfiguration.setDatabaseName(dbName);
            dbConfiguration.setUsername(username);
            dbConfiguration.setPassword(password);
            dbConfiguration.setAppUser(accountService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            dbConfigurationRepository.save(dbConfiguration);
            return dbConfiguration;
        }
        throw new DataSourceExistsException();
    }
    @Override
    public List<String> getTables() throws SQLException {
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
    public void loadDBConfiguration(Long id) {
        DBConfiguration configuration = dbConfigurationRepository.getOne(id);
        AppDataSource appDataSource = dataSourceFactory.create(configuration.getType(), configuration.getDatabaseName(), configuration.getHost(), configuration.getPort());
        configureDataSource(appDataSource.getUrl(), appDataSource.getDriver(), configuration.getUsername(), configuration.getPassword());
    }
    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(final String tableName) throws SQLException {
        if (getTables().contains(tableName)){
            this.tableName = tableName;
        }
        throw new InvalidTableNameException("Table "+tableName+" does not exist");
    }

    @Override
    public List<DBConfiguration> getConfigurations() {
        return dbConfigurationRepository.findAll();
    }

    @Override
    public boolean testConnection(String dbName, String username, String password, String host, int port, int type) throws SQLException {
        AppDataSource appDataSource = dataSourceFactory.create(type, dbName, host, port);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(appDataSource.getUrl());
        dataSource.setDriverClassName(appDataSource.getDriver());
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource.getConnection().isValid(5);
    }
}
