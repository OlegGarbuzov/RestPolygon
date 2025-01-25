package com.example.restpolygon.services;

import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.entity.User;
import com.example.restpolygon.feign.dto.FeignClientRequestDto;
import com.example.restpolygon.mapper.TickerMapperImpl;
import com.example.restpolygon.repo.TickerRepository;
import com.example.restpolygon.repo.UserRepository;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Data
public class TickerService {

	private final TickerRepository tickerRepository;
	private final TickerMapperImpl tickerMapper;
	private final UserRepository userRepository;

	public void saveTicker(Set<FeignClientRequestDto> feignClientRequestDtos) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Set<Ticker> tickers = new HashSet<>();
		for(FeignClientRequestDto feignClientRequestDto : feignClientRequestDtos) {
			Ticker ticker = tickerMapper.toTicker(feignClientRequestDto);
			ticker.setUser(user);
			tickers.add(ticker);
		}

		tickerRepository.saveAll(tickers);

	}

	public Iterable<Ticker> getTickers(String symbol) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return tickerRepository.findByUser(user);

	}

}
