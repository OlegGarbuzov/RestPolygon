package com.example.restpolygon.configs;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

	@Bean
	public ErrorDecoder errorDecoder () {
		return  new CustomErrorDecoder();
	}

	@Bean
	public Retryer retryer() {
		return new Retryer.Default(1000, 3000, 3);
	}

}
