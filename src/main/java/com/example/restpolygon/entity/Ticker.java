package com.example.restpolygon.entity;


import com.example.restpolygon.entity.classId.TickerId;
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
@Data
@AllArgsConstructor
@Table(name = "ticker")
@IdClass(TickerId.class)
public class Ticker {

	@Id
	private String symbol;
	@Id
	private LocalDate tickerDate;

	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;

}
