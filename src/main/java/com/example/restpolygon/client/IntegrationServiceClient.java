package com.example.restpolygon.client;

import com.example.restpolygon.client.dto.TickerDto;
import com.example.restpolygon.client.properties.ClientProperties;
import com.example.restpolygon.entity.Ticker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class IntegrationServiceClient {

	private final RestTemplate restTemplate;
	private final ClientProperties clientProperties;
	private final ObjectMapper objectMapper;

	public ResponseEntity<Void> saveTickers(String stocksTicker, String date) throws URISyntaxException, RestClientException, JsonProcessingException {

		URI uri = new URI(clientProperties.getIntegrationServiceRootUrl() + "/" + "AAPL" + "/" + "2025-01-16");

		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.set(clientProperties.getAuthorizationHeader(), clientProperties.getServiceApiKeyPrefix() + " " + clientProperties.getIntegrationServiceKey());

		HttpEntity<String> entity = new HttpEntity<>(httpHeader);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		TickerDto tickerDto = objectMapper.readValue(response.getBody(), TickerDto.class);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}


}
