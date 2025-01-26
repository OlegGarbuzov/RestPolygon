package com.example.restpolygon.feign.repo;

import com.example.restpolygon.configs.FeignConfig;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.feign.dto.FeignClientResponseDto;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "${client.integration.integrationServiceName}", url = "${client.integration.serviceRootUrl}", configuration = FeignConfig.class)
public interface IntegrationServiceFeign {
	String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
	String POLIGON_AGGREGATES_URL = "/{stocksTicker}/range/1/day/{from}/{to}";

	@GetMapping(POLIGON_AGGREGATES_URL)
	@RequestLine("GET /{stocksTicker}/range/1/day/{from}/{to}")
	FeignClientResponseDto getTicker(@RequestHeader(AUTHORIZATION_HEADER) String token, @PathVariable("stocksTicker") String stocksTicker, @PathVariable("from") String from, @PathVariable("to") String to) throws DataNotFoundException;

}
