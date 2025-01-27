package com.example.restpolygon.services;

import com.example.restpolygon.entity.TickerCatalog;
import com.example.restpolygon.error.exception.StockAlreadyExistException;
import com.example.restpolygon.repo.TickerCatalogRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class TickerCatalogService {

	private final TickerCatalogRepository tickerCatalogRepository;

	public boolean isExist(String symbol) {

		return tickerCatalogRepository.existsBySymbol(symbol);

	}

	public void addTicker(String ticker) throws StockAlreadyExistException {

		TickerCatalog tickerCatalog = TickerCatalog.builder()
				.symbol(ticker)
				.build();

		if(!tickerCatalogRepository.existsBySymbol(ticker)) {
			tickerCatalogRepository.save(tickerCatalog);
		} else {
			throw new StockAlreadyExistException("Stock already exist");
		}

	}

}
