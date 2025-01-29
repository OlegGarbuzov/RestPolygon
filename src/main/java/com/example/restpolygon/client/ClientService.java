package com.example.restpolygon.client;

import com.example.restpolygon.client.dto.ClientResponseDto;
import com.example.restpolygon.client.object.TickerData;
import com.example.restpolygon.client.service.ClientRequestValidation;
import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.error.exception.ClientRequestValidationException;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.mapper.TickerMapperImpl;
import com.example.restpolygon.services.TickerService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@Data
@Component
public class ClientService {

	private final TickerService tickerService;
	private final TickerMapperImpl tickerMapper;
	private final ClientRequestValidation clientRequestValidation;

	public ResponseEntity<ClientResponseDto> getUserStockRecord(String symbol) throws DataNotFoundException, ClientRequestValidationException {

		clientRequestValidation.tickerIsExistsInCatalogValidation(symbol);

		Iterable<Ticker> tickers = tickerService.getTickers(symbol);

		Set<TickerData> tickerDataSet = new TreeSet<>();
		for (Ticker ticker : tickers) {
			tickerDataSet.add(tickerMapper.toTickerData(ticker));
		}

		if(tickerDataSet.isEmpty()) throw new DataNotFoundException("No data");

		ClientResponseDto clientResponseDto = ClientResponseDto.builder()
				.id(UUID.randomUUID())
				.ticker(symbol)
				.data(tickerDataSet)
				.build();

		return new ResponseEntity<>(clientResponseDto, HttpStatus.OK);

	}

}
