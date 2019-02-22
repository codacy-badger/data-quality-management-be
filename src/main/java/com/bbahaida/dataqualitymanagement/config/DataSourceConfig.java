package com.bbahaida.dataqualitymanagement.config;

import com.bbahaida.dataqualitymanagement.datasources.AppDataSourceFactory;
import com.bbahaida.dataqualitymanagement.datasources.AppDataSourceFactoryImpl;
import com.bbahaida.dataqualitymanagement.utils.DataSourceConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource({DataSourceConstants.PROPERTY_FILE_PATH})
@EnableTransactionManagement
public class DataSourceConfig {



    // Start DataSources Configuration

    @Bean
    public AppDataSourceFactory appDataSourceFactory(){
        return new AppDataSourceFactoryImpl();
    }

    @Bean
    @Primary
    @Qualifier(DataSourceConstants.JOB_DS_QUALIFIER)
    public DriverManagerDataSource jobDataSource(
            @Value(DataSourceConstants.JOB_URL) String url,
            @Value(DataSourceConstants.JOB_DRIVER) String driver,
            @Value(DataSourceConstants.JOB_USER) String username,
            @Value(DataSourceConstants.JOB_PASSWORD) String password){
        return getDataSource(url, driver, username, password);
    }


    @Bean
    @Qualifier(DataSourceConstants.CLIENT_DS_QUALIFIER)
    public DriverManagerDataSource clientDataSource(
            @Value(DataSourceConstants.CLIENT_URL) String url,
            @Value(DataSourceConstants.CLIENT_DRIVER) String driver,
            @Value(DataSourceConstants.CLIENT_USER) String username,
            @Value(DataSourceConstants.CLIENT_PASSWORD) String password){
        return getDataSource(url, driver, username, password);
    }

    private DriverManagerDataSource getDataSource(String url, String driver, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    // Start Jpa Datasource configuration

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier(DataSourceConstants.JOB_DS_QUALIFIER) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(getPackagesToScan());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalJpaProperties());

        return em;
    }

    private String[] getPackagesToScan() {
        return new String[] { DataSourceConstants.ENTITIES_PACKAGE };
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    private Properties additionalJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");

        return properties;
    }

    // End Jpa Datasource configuration

}
