package com.bbahaida.dataqualitymanagement.utils;

public interface DataSourceConstants {
    String PROPERTY_FILE_PATH = "classpath:persistence-multiple-db.properties";

    int MYSQL = 1;
    int POSTGRES = 2;
    int ORACLE = 3;


    String ENTITIES_PACKAGE = "com.bbahaida.dataqualitymanagement.entities";
    String JOB_URL = "${job.datasource.url:jdbc:mysql://localhost/data-quality-management-db?serverTimezone=UTC}";
    String JOB_DRIVER = "${job.datasource.driver-class-name:com.mysql.jdbc.Driver}";
    String JOB_USER = "${job.datasource.username:root}";
    String JOB_PASSWORD = "${job.datasource.password:}";
    String CLIENT_URL = "${client.datasource.url:jdbc:mysql://localhost/client-db?serverTimezone=UTC}";
    String CLIENT_DRIVER = "${client.datasource.driver-class-name:com.mysql.jdbc.Driver}";
    String CLIENT_USER = "${client.datasource.username:root}";
    String CLIENT_PASSWORD = "${client.datasource.password:}";


    String CLIENT_DS_QUALIFIER = "client-ds";
    String JOB_DS_QUALIFIER = "job-ds";
}
