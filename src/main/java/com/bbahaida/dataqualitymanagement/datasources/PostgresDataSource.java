package com.bbahaida.dataqualitymanagement.datasources;

public class PostgresDataSource extends AppDataSource {

    PostgresDataSource(String databaseName, String host, int port) {
        super(databaseName, host, port);
    }

    @Override
    public String getUrl() {
        return "jdbc:postgresql://" + this.getUri();
    }

    @Override
    public String getDriver() {
        return "org.postgresql.Driver";
    }
}
