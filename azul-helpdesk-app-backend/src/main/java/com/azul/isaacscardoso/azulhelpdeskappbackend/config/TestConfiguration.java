package com.azul.isaacscardoso.azulhelpdeskappbackend.config;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfiguration {

    private DBService dbService;

    public TestConfiguration() { }

    @Autowired
    public TestConfiguration(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public void instantiateDatabase() {
        this.dbService.instatiateDatabase();
    }
}
