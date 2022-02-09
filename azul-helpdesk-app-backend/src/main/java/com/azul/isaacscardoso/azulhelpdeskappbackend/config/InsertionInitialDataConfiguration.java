package com.azul.isaacscardoso.azulhelpdeskappbackend.config;

import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertionInitialDataConfiguration {

    private DBService dbService;

    public InsertionInitialDataConfiguration() { }

    @Autowired
    public InsertionInitialDataConfiguration(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public void instantiateDatabase() {
        this.dbService.instatiateDatabase();
    }
}
