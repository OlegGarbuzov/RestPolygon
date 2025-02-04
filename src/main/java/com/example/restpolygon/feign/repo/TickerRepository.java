package com.example.restpolygon.feign.repo;

import com.example.restpolygon.feign.entity.Ticker;
import com.example.restpolygon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TickerRepository extends JpaRepository<Ticker, Long> {
	List<Ticker> findByUserAndSymbolOrderByTickerDateAsc(User user, String symbol);

	boolean existsByUserAndSymbolAndTickerDate(User user, String symbol, LocalDate tickerDate);

}
