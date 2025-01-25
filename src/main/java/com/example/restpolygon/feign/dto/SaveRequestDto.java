package com.example.restpolygon.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRequestDto {

	private String ticker;
	private LocalDate start;
	private LocalDate end;

}
