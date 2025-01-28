package com.example.restpolygon.client.service;

import com.example.restpolygon.error.exception.ClientRequestValidationException;
import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.services.TickerCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientRequestValidation {

	private final TickerCatalogService tickerCatalogService;

	public void stockSaveValidation (SaveRequestDto userRequest) throws ClientRequestValidationException {

		if(userRequest.getEnd().isBefore(userRequest.getStart())) {
			throw new ClientRequestValidationException("Invalid date range");
		}

	}

	public void tickerIsExistsInCatalogValidation (String stock) throws ClientRequestValidationException {

		if(!tickerCatalogService.isExist(stock)) {
			throw new ClientRequestValidationException("Unknown ticker");
		}

	}

}
