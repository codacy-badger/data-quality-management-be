package com.bbahaida.dataqualitymanagement.batch.config;

import com.bbahaida.dataqualitymanagement.utils.DataSourceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class JobDatabaseSetup {

    private DataSource dataSource;
    private ResourceLoader resourceLoader;

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    public void setDataSource(@Qualifier(DataSourceConstants.JOB_DS_QUALIFIER) DataSource dataSource){
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void setupJobSchema() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(this.resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-drop-mysql.sql"));
        populator.addScript(this.resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-mysql.sql"));
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator, this.dataSource);
    }
}
