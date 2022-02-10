package com.azul.isaacscardoso.azulhelpdeskappbackend.config;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
public class DevelopmentConfiguration {

    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String value;

    public DevelopmentConfiguration() { }

    @Autowired
    public DevelopmentConfiguration(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public void instantiateDatabase() {
        if (this.value.equals("create"))
            this.dbService.instatiateDatabase();
    }
}
