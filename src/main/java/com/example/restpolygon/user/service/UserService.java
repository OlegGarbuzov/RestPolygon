package com.example.restpolygon.user.service;

import com.example.restpolygon.feign.entity.Ticker;
import com.example.restpolygon.feign.exception.DataNotFoundException;
import com.example.restpolygon.feign.service.TickerService;
import com.example.restpolygon.mapper.TickerMapperImpl;
import com.example.restpolygon.user.dto.ClientResponseDto;
import com.example.restpolygon.user.dto.TickerData;
import com.example.restpolygon.user.entity.User;
import com.example.restpolygon.user.repo.UserRepository;
import com.example.restpolygon.user.service.userValidation.UserValidationStrategyImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final TickerService tickerService;
	private final TickerMapperImpl tickerMapper;
	private final UserValidationStrategyImpl userValidation;
	private final UserRequestValidation userRequestValidation;

	public void create(User user) {
		userValidation.validation(user);
		userRepository.save(user);
	}

	public ClientResponseDto getUserStockRecord(String symbol) {
		userRequestValidation.tickerIsExistsInCatalogValidation(symbol);

		List<Ticker> tickers = tickerService.getTickers(symbol);

		Set<TickerData> tickerDataSet = new TreeSet<>();
		tickers.forEach(t -> tickerDataSet.add(tickerMapper.toTickerData(t)));

		if(tickerDataSet.isEmpty()) throw new DataNotFoundException("No data");

		return ClientResponseDto.builder()
				.id(UUID.randomUUID())
				.ticker(symbol)
				.data(tickerDataSet)
				.build();

	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
