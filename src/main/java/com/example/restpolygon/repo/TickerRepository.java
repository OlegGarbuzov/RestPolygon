package com.example.restpolygon.repo;

import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {
	Iterable<Ticker> findByUserAndSymbolOrderByTickerDateAsc(User user, String symbol);

	boolean existsByUserAndSymbolAndTickerDate(User user, String symbol, LocalDate tickerDate);

}
