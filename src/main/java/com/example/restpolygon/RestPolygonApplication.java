package com.example.restpolygon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RestPolygonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestPolygonApplication.class, args);
	}

}

