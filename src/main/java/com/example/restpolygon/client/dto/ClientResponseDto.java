package com.example.restpolygon.client.dto;

import com.example.restpolygon.client.object.TickerData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {

	private UUID id;
	private String ticker;
	private Set<TickerData> data;

}
