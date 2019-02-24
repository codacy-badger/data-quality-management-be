package com.bbahaida.dataqualitymanagement.datasources;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AppDataSource {

    private String databaseName;
    private String host;
    private int port;


    String getUri() {
        return host + ":" + port + "/" + databaseName;
    }

    public abstract String getUrl();

    public abstract String getDriver();
}
