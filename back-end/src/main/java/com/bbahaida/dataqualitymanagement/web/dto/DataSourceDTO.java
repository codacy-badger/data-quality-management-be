package com.bbahaida.dataqualitymanagement.web.dto;

public class DataSourceDTO {
    private int type;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private String host;

    public DataSourceDTO() {
    }

    public DataSourceDTO(int type, int port, String databaseName, String username, String password, String host) {
        this.type = type;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.host = host;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
