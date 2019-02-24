package com.bbahaida.dataqualitymanagement.datasources;

public class MySqlDataSource extends AppDataSource {

    MySqlDataSource(String databaseName, String host, int port) {
        super(databaseName, host, port);
    }

    @Override
    public String getUrl() {
        return "jdbc:mysql://" + this.getUri() + "?serverTimezone=UTC";
    }

    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }
}
