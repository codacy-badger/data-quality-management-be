package com.bbahaida.dataqualitymanagement.datasources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MySqlDataSource extends AppDataSource {

    private String databaseName;
    private String host;
    private int port;


    @Override
    public String getUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + databaseName +"?serverTimezone=UTC";
    }

    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }
}
