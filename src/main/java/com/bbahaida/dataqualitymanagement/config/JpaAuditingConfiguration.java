package com.bbahaida.dataqualitymanagement.config;

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
