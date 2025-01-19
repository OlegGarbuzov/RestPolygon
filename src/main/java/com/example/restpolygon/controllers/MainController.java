package com.example.restpolygon.controllers;

import com.example.restpolygon.client.IntegrationServiceClient;
import com.example.restpolygon.controllers.doc.MainControllerDocumentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.net.URISyntaxException;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MainController implements MainControllerDocumentation {

	private final IntegrationServiceClient integrationServiceClient;
	private final String ROOT = "/stock";

	@PostMapping(ROOT)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> saveStockRecord() throws URISyntaxException, RestClientException, JsonProcessingException {
		return integrationServiceClient.saveTickers("2", "2");

	}

	@GetMapping("/stock/{symbol}")
	@PreAuthorize("hasRole('USER')")
	public String getStockRecord(@PathVariable String symbol) {
		System.out.println(2);
		return symbol;
	}

}
