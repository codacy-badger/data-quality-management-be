package com.bbahaida.dataqualitymanagement.config;

import com.bbahaida.dataqualitymanagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/*@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")*/
public class JpaAuditingConfiguration {


    /*private AccountService accountService;

    public JpaAuditingConfiguration(AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> Optional.ofNullable(accountService.getUserId(SecurityContextHolder.getContext().getAuthentication().getName()));
    }*/
}
