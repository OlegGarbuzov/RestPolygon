package com.example.restpolygon.repo;

import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {
	Iterable<Ticker> findByUserAndSymbol(User user, String symbol);

}
