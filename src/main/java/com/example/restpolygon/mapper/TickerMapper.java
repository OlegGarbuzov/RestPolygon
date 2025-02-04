package com.example.restpolygon.mapper;

import com.example.restpolygon.feign.entity.Ticker;
import com.example.restpolygon.user.dto.TickerData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TickerMapper {
	@Mapping(source = "tickerDate", target = "date")
	TickerData toTickerData(Ticker ticker);

}
