package com.bbahaida.dataqualitymanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        Contact contact = new Contact(
                "Brahim Bahaida",
                "https://www.linkedin.com/in/bbahaida/",
                "bbahieda@outlook.com");

        return new ApiInfo(
                "Data Quality Management Platform",
                "Data Quality Management Platform",
                "1.0.0",
                "Terms of Service: blah blah",
                contact,
                "CC BY-NC-ND",
                "https://creativecommons.org/licenses/by-nc-nd/4.0/",
                new ArrayList<>()
        );
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/docs/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController("/docs/configuration/security", "/configuration/security");
        registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/docs", "/docs/swagger-ui.html");
        registry.addRedirectViewController("/docs/", "/docs/swagger-ui.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/docs/**").addResourceLocations("classpath:/META-INF/resources/");
    }

}
