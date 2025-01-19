package com.example.restpolygon.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticker")
public class Ticker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ticker;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@Temporal(TemporalType.DATE) @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) private LocalDate from;

	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;

}
