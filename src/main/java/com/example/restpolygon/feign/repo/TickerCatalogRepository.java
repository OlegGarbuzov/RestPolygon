package com.example.restpolygon.feign.repo;

import com.example.restpolygon.feign.entity.TickerCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerCatalogRepository extends JpaRepository<TickerCatalog, Long> {
	boolean existsBySymbol(String symbol);

}
