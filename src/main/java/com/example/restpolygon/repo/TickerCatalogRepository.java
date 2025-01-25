package com.example.restpolygon.repo;

import com.example.restpolygon.entity.TickerCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerCatalogRepository extends JpaRepository<TickerCatalog, Long> {
	boolean existsBySymbol(String symbol);

}
