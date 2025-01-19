package com.example.restpolygon.mapper;

import com.example.restpolygon.client.dto.TickerDto;
import com.example.restpolygon.entity.Ticker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TickerMapper {

	Ticker toTicker(TickerDto tickerDto);
	TickerDto toTickerDto(Ticker ticker);


}
