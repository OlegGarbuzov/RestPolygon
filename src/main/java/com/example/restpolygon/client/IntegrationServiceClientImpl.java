package com.example.restpolygon.client;

import com.example.restpolygon.client.dto.TickerDto;
import com.example.restpolygon.client.properties.ClientProperties;
import com.example.restpolygon.client.repo.IntegrationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class IntegrationServiceClientImpl {

	private final IntegrationServiceClient integrationServiceClient;
	private final ClientProperties clientProperties;

	public ResponseEntity<Void> saveTickers(String stocksTicker, String date) throws RestClientException {
		String authorizationToken = clientProperties.getIntegrationServiceAuthorizationPrefix() + " " + clientProperties.getIntegrationServiceKey();
		TickerDto tickerDto = integrationServiceClient.getTicker(authorizationToken, stocksTicker, date);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
