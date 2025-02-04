package com.example.restpolygon.feign.service;

import com.example.restpolygon.feign.entity.TickerCatalog;
import com.example.restpolygon.feign.exception.TickerCatalogException;
import com.example.restpolygon.feign.repo.TickerCatalogRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class TickerCatalogService {

	private final TickerCatalogRepository tickerCatalogRepository;

	public boolean isExist(String symbol) {
		return tickerCatalogRepository.existsBySymbol(symbol);
	}

	public void addTicker(String ticker) {
		TickerCatalog tickerCatalog = TickerCatalog.builder()
				.symbol(ticker)
				.build();

		if(tickerCatalogRepository.existsBySymbol(ticker)) throw new TickerCatalogException("Ticker is already exists");

		tickerCatalogRepository.save(tickerCatalog);
	}

}
