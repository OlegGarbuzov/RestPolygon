package com.example.restpolygon.services;

import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.entity.User;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.feign.dto.FeignClientResponseDto;
import com.example.restpolygon.feign.object.FeignClientResponseResultDto;
import com.example.restpolygon.mapper.TickerMapperImpl;
import com.example.restpolygon.repo.TickerRepository;
import com.example.restpolygon.repo.UserRepository;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import java.util.TreeSet;

@Service
@Data
public class TickerService {

	private final TickerRepository tickerRepository;
	private final TickerMapperImpl tickerMapper;
	private final UserRepository userRepository;

	public void saveTicker(FeignClientResponseDto feignClientResponseDto) throws DataNotFoundException {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Set<FeignClientResponseResultDto> results = feignClientResponseDto.getResults();
		if(results == null) throw new DataNotFoundException("Empty result");

		Set<Ticker> tickers = new TreeSet<>();
		for(FeignClientResponseResultDto result : results) {

			Instant instant = Instant.ofEpochMilli(result.getT().longValue());
			LocalDate tickerDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

			if(tickerRepository.existsByUserAndSymbolAndTickerDate(user, feignClientResponseDto.getTicker(), tickerDate)) continue;

			Ticker ticker = Ticker.builder()
					.symbol(feignClientResponseDto.getTicker())
					.tickerDate(tickerDate)
					.user(user)
					.open(result.getO())
					.close(result.getC())
					.high(result.getH())
					.low(result.getL())
					.build();

			tickers.add(ticker);

		}

		tickerRepository.saveAll(tickers);

	}

	public Iterable<Ticker> getTickers(String symbol) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return tickerRepository.findByUserAndSymbolOrderByTickerDateAsc(user, symbol);

	}

}
