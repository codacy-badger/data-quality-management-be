package com.bbahaida.dataqualitymanagement.repositories;

import com.bbahaida.dataqualitymanagement.entities.AppDBItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDBItemRepository extends JpaRepository<AppDBItem, Long> {
}
