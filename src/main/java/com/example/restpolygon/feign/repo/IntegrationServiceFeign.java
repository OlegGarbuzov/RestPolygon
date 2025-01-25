package com.example.restpolygon.feign.repo;

import com.example.restpolygon.configs.FeignConfig;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.feign.dto.FeignClientRequestDto;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "${client.integration.integrationServiceName}", url = "${client.integration.serviceRootUrl}", configuration = FeignConfig.class)
public interface IntegrationServiceFeign {
	String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;

	@GetMapping("/{stocksTicker}/{date}")
	@RequestLine("GET /{stocksTicker}/{date}")
	FeignClientRequestDto getTicker(@RequestHeader(AUTHORIZATION_HEADER) String token, @PathVariable("stocksTicker") String stocksTicker, @PathVariable("date") String date) throws DataNotFoundException;

}
