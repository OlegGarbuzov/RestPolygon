package com.example.restpolygon.services;

import com.example.restpolygon.entity.TickerCatalog;
import com.example.restpolygon.repo.TickerCatalogRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Data
@Service
public class TickerCatalogService {

	private final TickerCatalogRepository tickerCatalogRepository;

	public boolean isExist(String symbol) {

		return tickerCatalogRepository.existsBySymbol(symbol);

	}

	public ResponseEntity<Void> addTicker(String ticker) {

		TickerCatalog tickerCatalog = TickerCatalog.builder()
				.symbol(ticker)
				.build();

		if(!tickerCatalogRepository.existsBySymbol(ticker)) {
			tickerCatalogRepository.save(tickerCatalog);
		} else {
			new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

}
