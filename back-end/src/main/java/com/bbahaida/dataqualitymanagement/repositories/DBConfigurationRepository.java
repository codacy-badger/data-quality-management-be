package com.bbahaida.dataqualitymanagement.repositories;

import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBConfigurationRepository extends JpaRepository<DBConfiguration, Long> {
    DBConfiguration findDBConfigurationByUrlAndTypeAndUsername(String url, int type, String username);
}
