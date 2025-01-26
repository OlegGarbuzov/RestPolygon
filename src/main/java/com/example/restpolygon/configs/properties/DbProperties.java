package com.example.restpolygon.configs.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("postgres.db")
@EnableConfigurationProperties(DbProperties.class)
public class DbProperties {

	private String url;
	private String user;
	private String password;

}
