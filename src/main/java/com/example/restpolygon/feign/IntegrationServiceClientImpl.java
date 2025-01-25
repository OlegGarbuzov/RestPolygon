package com.example.restpolygon.feign;

import com.example.restpolygon.client.service.ClientRequestValidation;
import com.example.restpolygon.error.exception.ClientRequestValidationException;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.feign.dto.FeignClientRequestDto;
import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.feign.properties.FeignProperties;
import com.example.restpolygon.feign.repo.IntegrationServiceFeign;
import com.example.restpolygon.services.TickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class IntegrationServiceClientImpl {

	private final IntegrationServiceFeign integrationServiceFeign;
	private final FeignProperties feignProperties;
	private final TickerService tickerService;
	private final ClientRequestValidation clientRequestValidation;

	public ResponseEntity<Void> saveTickers(SaveRequestDto saveRequestDto) throws DataNotFoundException, ClientRequestValidationException {

		clientRequestValidation.stockSaveValidation(saveRequestDto);

		String authorizationToken = feignProperties.getServiceAuthorizationPrefix() + " " + feignProperties.getServiceKey();
		String stocksTicker = saveRequestDto.getTicker();

		Set<FeignClientRequestDto> feignClientRequestDtos = new HashSet<>();
		LocalDate requestDate = saveRequestDto.getStart();
		while(!requestDate.isAfter(saveRequestDto.getEnd())) {

			feignClientRequestDtos.add(integrationServiceFeign.getTicker(authorizationToken, stocksTicker, requestDate.format(DateTimeFormatter.ISO_DATE)));
			requestDate = requestDate.plusDays(1);

		}

		tickerService.saveTicker(feignClientRequestDtos);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

}
