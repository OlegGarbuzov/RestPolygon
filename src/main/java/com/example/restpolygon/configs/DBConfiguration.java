package com.example.restpolygon.configs;

import com.example.restpolygon.configs.properties.DbProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class DBConfiguration {

    private DbProperties dbProperties;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setUrl(dbProperties.getUrl());
        driverManagerDataSource.setUsername(dbProperties.getUser());
        driverManagerDataSource.setPassword(dbProperties.getPassword());
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");

        return driverManagerDataSource;

    }

}
