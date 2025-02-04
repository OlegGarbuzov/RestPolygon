package com.example.restpolygon.feign.service;

import com.example.restpolygon.feign.dto.FeignClientResponseResultDto;
import com.example.restpolygon.feign.entity.Ticker;
import com.example.restpolygon.feign.repo.TickerRepository;
import com.example.restpolygon.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.TreeSet;

@Service
@Getter
@RequiredArgsConstructor
public class SaveTickerResultValidation {

	private final TickerRepository tickerRepository;

	public Set<Ticker> filter(Set<FeignClientResponseResultDto> feignClientResponseResults, String symbol) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<Ticker> tickers = new TreeSet<>();
		for(FeignClientResponseResultDto result : feignClientResponseResults) {

			Instant instant = Instant.ofEpochMilli(result.getT().longValue());
			LocalDate tickerDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

			if(tickerRepository.existsByUserAndSymbolAndTickerDate(user, symbol, tickerDate)) continue;

			Ticker ticker = Ticker.builder()
					.symbol(symbol)
					.tickerDate(tickerDate)
					.user(user)
					.open(result.getO())
					.close(result.getC())
					.high(result.getH())
					.low(result.getL())
					.build();

			tickers.add(ticker);

		}

		return tickers;
	}

}
