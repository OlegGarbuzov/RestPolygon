package com.example.restpolygon.repo;

import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntegrationRepository extends JpaRepository<Ticker, Long> {
	Optional<Ticker> findByUserAndTicker(User user, String ticker);

}
