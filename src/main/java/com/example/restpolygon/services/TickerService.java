package com.example.restpolygon.services;

import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.entity.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Data
public class TickerService {

	private final TickerRepository tickerRepository;
	private final TickerMapperImpl tickerMapper;
	private final UserRepository userRepository;

	public void saveTicker(FeignClientResponseDto feignClientResponseDto) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<FeignClientResponseResultDto> results = feignClientResponseDto.getResults();
		Set<Ticker> tickers = new HashSet<>();
		for(FeignClientResponseResultDto result : results) {

			Instant instant = Instant.ofEpochMilli(result.getT().longValue());
			LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

			Ticker ticker = new Ticker();
			ticker.setSymbol(feignClientResponseDto.getTicker());
			ticker.setTickerDate(localDate);
			ticker.setUser(user);
			ticker.setOpen(result.getO());
			ticker.setClose(result.getC());
			ticker.setHigh(result.getH());
			ticker.setLow(result.getL());

			tickers.add(ticker);

		}

		tickerRepository.saveAll(tickers);

	}

	public Iterable<Ticker> getTickers(String symbol) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return tickerRepository.findByUserAndSymbol(user, symbol);

	}

}
