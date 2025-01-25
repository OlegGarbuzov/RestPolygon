package com.example.restpolygon.feign.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("client.integration")
@EnableConfigurationProperties(FeignProperties.class)
public class FeignProperties {

	private String integrationServiceName;
	private String serviceRootUrl;
	private String serviceAuthorizationPrefix;
	private String serviceKey;

}
