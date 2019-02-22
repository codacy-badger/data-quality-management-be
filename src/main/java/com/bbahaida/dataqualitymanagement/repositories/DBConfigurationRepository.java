package com.bbahaida.dataqualitymanagement.repositories;

import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBConfigurationRepository extends JpaRepository<DBConfiguration, Long> {
    DBConfiguration findDBConfigurationByDatabaseNameAndUsernameAndPasswordAndHostAndPortAndType(final String dbName, final String username, final String password, final String host, final int port, final int type);
}
