package com.example.restpolygon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ticker")
public class Ticker implements Comparable<Ticker> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String symbol;

	private LocalDate tickerDate;

	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;

	@Override
	public int compareTo(Ticker o) {
		return this.tickerDate.compareTo(o.tickerDate);
	}
}
