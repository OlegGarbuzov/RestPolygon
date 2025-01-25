package com.example.restpolygon.mapper;

import com.example.restpolygon.client.object.TickerData;
import com.example.restpolygon.entity.Ticker;
import com.example.restpolygon.feign.dto.FeignClientRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TickerMapper {

	@Mapping(source = "from", target = "tickerDate")
	Ticker toTicker(FeignClientRequestDto tickerDto);

	@Mapping(source = "tickerDate", target = "date")
	TickerData toTickerData(Ticker ticker);

}
