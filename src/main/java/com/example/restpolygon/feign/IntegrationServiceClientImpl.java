package com.example.restpolygon.feign;

import com.example.restpolygon.feign.dto.FeignClientResponseDto;
import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.feign.properties.FeignProperties;
import com.example.restpolygon.feign.service.TickerService;
import com.example.restpolygon.user.service.UserRequestValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class IntegrationServiceClientImpl {

	private final IntegrationServiceFeign integrationServiceFeign;
	private final FeignProperties feignProperties;
	private final TickerService tickerService;
	private final UserRequestValidation userRequestValidation;

	public void saveTickers(SaveRequestDto saveRequestDto){
		userRequestValidation.stockSaveValidation(saveRequestDto);
		userRequestValidation.tickerIsExistsInCatalogValidation(saveRequestDto.getTicker());

		String authorizationToken = feignProperties.getServiceAuthorizationPrefix() + feignProperties.getServiceKey();
		String stocksTicker = saveRequestDto.getTicker();

		FeignClientResponseDto feignClientResponseDto = integrationServiceFeign.getTicker(authorizationToken, stocksTicker, saveRequestDto.getStart().format(DateTimeFormatter.ISO_DATE), saveRequestDto.getEnd().format(DateTimeFormatter.ISO_DATE));

		tickerService.saveTicker(feignClientResponseDto);
	}

}
