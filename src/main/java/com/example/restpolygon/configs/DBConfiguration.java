package com.example.restpolygon.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/test");
        driverManagerDataSource.setUsername("test");
        driverManagerDataSource.setPassword("test");
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");

        return driverManagerDataSource;

    }

}
