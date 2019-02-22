package com.bbahaida.dataqualitymanagement.batch.config.job;

import com.bbahaida.dataqualitymanagement.utils.DataSourceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JobTransactionManager {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier(DataSourceConstants.JOB_DS_QUALIFIER) DataSource ds){
        this.dataSource = ds;
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
