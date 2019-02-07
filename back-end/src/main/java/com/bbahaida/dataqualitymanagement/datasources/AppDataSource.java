package com.bbahaida.dataqualitymanagement.datasources;

import com.bbahaida.dataqualitymanagement.web.dto.DataSourceDTO;

public abstract class AppDataSource {
    public abstract String getUrl();
    public abstract String getDriver();
}
