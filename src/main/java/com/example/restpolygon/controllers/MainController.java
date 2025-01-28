package com.example.restpolygon.controllers;

import com.example.restpolygon.client.ClientService;
import com.example.restpolygon.client.dto.ClientResponseDto;
import com.example.restpolygon.controllers.doc.MainControllerDocumentation;
import com.example.restpolygon.error.exception.ClientRequestValidationException;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.error.exception.StockAlreadyExistException;
import com.example.restpolygon.feign.IntegrationServiceClientImpl;
import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.services.TickerCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("api/v1/stock")
@RequiredArgsConstructor
public class MainController implements MainControllerDocumentation {

	private final IntegrationServiceClientImpl integrationServiceClient;
	private final ClientService clientService;
	private final TickerCatalogService tickerCatalogService;

	public static final String ADD_TICKER_URI = "/catalog/addTicker";

	@PostMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> saveStockRecord(@RequestBody SaveRequestDto saveRequestDto) throws RestClientException, DataNotFoundException, ClientRequestValidationException {
		return integrationServiceClient.saveTickers(saveRequestDto);
	}

	@GetMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ClientResponseDto> getStockRecord(@RequestParam("ticker") String ticker) throws DataNotFoundException, ClientRequestValidationException {
		return clientService.getUserStockRecord(ticker);
	}

	/*
	 В учебных целях авторизация запроса отключена
	 */
	@PostMapping("/catalog/addTicker")
	public ResponseEntity<Void> addStockInCatalog(@RequestParam("ticker") String ticker) throws StockAlreadyExistException {
		return tickerCatalogService.addTicker(ticker);
	}

}
