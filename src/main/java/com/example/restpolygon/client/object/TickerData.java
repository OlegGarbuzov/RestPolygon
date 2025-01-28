package com.example.restpolygon.client.object;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TickerData implements Comparable<TickerData>{

	private LocalDate date;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;

	@Override
	public int compareTo(TickerData o) {
		return this.date.compareTo(o.date);
	}

}
