package com.example.restpolygon.feign.service;

import com.example.restpolygon.feign.dto.FeignClientResponseDto;
import com.example.restpolygon.feign.dto.FeignClientResponseResultDto;
import com.example.restpolygon.feign.entity.Ticker;
import com.example.restpolygon.feign.exception.DataNotFoundException;
import com.example.restpolygon.feign.repo.TickerRepository;
import com.example.restpolygon.user.entity.User;
import com.example.restpolygon.user.repo.UserRepository;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Data
public class TickerService {

	private final TickerRepository tickerRepository;
	private final UserRepository userRepository;
	private final SaveTickerResultValidation saveTickerResultValidation;

	public void saveTicker(FeignClientResponseDto feignClientResponseDto){
		Set<FeignClientResponseResultDto> results = feignClientResponseDto.getResults();
		if(results == null) throw new DataNotFoundException("Empty result");

		Set<Ticker> tickers = saveTickerResultValidation.filter(results, feignClientResponseDto.getTicker());

		tickerRepository.saveAll(tickers);
	}

	public List<Ticker> getTickers(String symbol) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return tickerRepository.findByUserAndSymbolOrderByTickerDateAsc(user, symbol);

	}

}
