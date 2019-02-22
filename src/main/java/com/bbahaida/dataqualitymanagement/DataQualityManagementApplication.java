package com.bbahaida.dataqualitymanagement;

import com.bbahaida.dataqualitymanagement.entities.AppRole;
import com.bbahaida.dataqualitymanagement.entities.AppUser;
import com.bbahaida.dataqualitymanagement.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.stream.Stream;

@EnableSwagger2
@SpringBootApplication
public class DataQualityManagementApplication { /*extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DataQualityManagementApplication.class);
	}*/

    public static void main(String[] args) {
        SpringApplication.run(DataQualityManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.saveRole(new AppRole("SIMPLE_USER"));
            accountService.saveRole(new AppRole("SUPER_USER"));
            accountService.saveRole(new AppRole("MANAGER"));

            Stream.of("super", "simple", "manager").forEach(u -> {
                AppUser user = accountService.saveUser(u, "1234", "1234");
                accountService.activateUser(user.getId());
            });

            accountService.addRoleToUser("simple", "SIMPLE_USER");
            accountService.addRoleToUser("super", "SUPER_USER");
            accountService.addRoleToUser("manager", "MANAGER");
            accountService.addRoleToUser("manager", "SIMPLE_USER");


        };
    }
}

