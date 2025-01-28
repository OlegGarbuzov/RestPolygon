package com.example.restpolygon.feign.object;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FeignClientResponseResultDto {

	private BigDecimal o;
	private BigDecimal c;
	private BigDecimal h;
	private BigDecimal l;
	private BigDecimal t;

}
