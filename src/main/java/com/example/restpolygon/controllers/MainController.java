package com.example.restpolygon.controllers;

import com.example.restpolygon.controllers.doc.MainControllerDocumentation;
import com.example.restpolygon.feign.IntegrationServiceClientImpl;
import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.feign.service.TickerCatalogService;
import com.example.restpolygon.user.dto.ClientResponseDto;
import com.example.restpolygon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stock")
@RequiredArgsConstructor
public class MainController implements MainControllerDocumentation {

	private final IntegrationServiceClientImpl integrationServiceClient;
	private final UserService userService;
	private final TickerCatalogService tickerCatalogService;

	public static final String ADD_TICKER_URI = "/catalog/addTicker";

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> saveStockRecord(@RequestBody SaveRequestDto saveRequestDto) {
		integrationServiceClient.saveTickers(saveRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ClientResponseDto> getStockRecord(@RequestParam("ticker") String ticker) {
		ClientResponseDto clientResponseDto = userService.getUserStockRecord(ticker);
		return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);
	}

	/*
	 В учебных целях авторизация запроса отключена
	 */
	@PostMapping("/catalog/addTicker")
	public ResponseEntity<Void> addStockInCatalog(@RequestParam("ticker") String ticker) {
		tickerCatalogService.addTicker(ticker);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
