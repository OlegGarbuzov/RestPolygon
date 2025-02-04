package com.example.restpolygon.feign;

import com.example.restpolygon.feign.configs.FeignConfig;
import com.example.restpolygon.feign.dto.FeignClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "${client.integration.integrationServiceName}", url = "${client.integration.serviceRootUrl}", configuration = FeignConfig.class)
public interface IntegrationServiceFeign {
	String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
	String POLYGON_AGGREGATES_URL = "/{stocksTicker}/range/1/day/{from}/{to}";

	@GetMapping(POLYGON_AGGREGATES_URL)
	FeignClientResponseDto getTicker(@RequestHeader(AUTHORIZATION_HEADER) String token, @PathVariable("stocksTicker") String stocksTicker, @PathVariable("from") String from, @PathVariable("to") String to);

}
