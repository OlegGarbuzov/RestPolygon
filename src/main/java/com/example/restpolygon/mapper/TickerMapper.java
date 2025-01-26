package com.example.restpolygon.mapper;

import com.example.restpolygon.client.object.TickerData;
import com.example.restpolygon.entity.Ticker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TickerMapper {
	@Mapping(source = "tickerDate", target = "date")
	TickerData toTickerData(Ticker ticker);

}
