package com.example.restpolygon.feign.dto;

import com.example.restpolygon.feign.object.FeignClientResponseResultDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeignClientResponseDto {

	private String ticker;
	private Set<FeignClientResponseResultDto> results;

}
