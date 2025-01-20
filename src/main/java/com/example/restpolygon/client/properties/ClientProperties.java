package com.example.restpolygon.client.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("client")
@EnableConfigurationProperties(ClientProperties.class)
public class ClientProperties {

	private String integrationServiceName;
	private String integrationServiceRootUrl;
	private String integrationServiceAuthorizationPrefix;
	private String integrationServiceKey;
	private String authorizationHeader;

}
