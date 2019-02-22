package com.bbahaida.dataqualitymanagement.datasources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostgresDataSource extends AppDataSource {

    private String databaseName;
    private String host;
    private int port;


    @Override
    public String getUrl() {
        return "jdbc:postgresql://" + host + ":" + port + "/"+databaseName;
    }

    @Override
    public String getDriver() {
        return "org.postgresql.Driver";
    }
}
