package com.example.restpolygon.user.service;

import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.feign.exception.ClientRequestValidationException;
import com.example.restpolygon.feign.service.TickerCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestValidation {

	private final TickerCatalogService tickerCatalogService;

	public void stockSaveValidation (SaveRequestDto userRequest) {

		if(userRequest.getEnd().isBefore(userRequest.getStart())) {
			throw new ClientRequestValidationException("Invalid date range");
		}

	}

	public void tickerIsExistsInCatalogValidation (String stock) {

		if(!tickerCatalogService.isExist(stock)) {
			throw new ClientRequestValidationException("Unknown ticker");
		}

	}

}
