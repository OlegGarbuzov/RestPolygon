package com.example.restpolygon.client.repo;

import com.example.restpolygon.client.dto.TickerDto;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "${integrationServiceName}", url = "${client.integrationServiceRootUrl}")
public interface IntegrationServiceClient {

	@GetMapping("/{stocksTicker}/{date}")
	@RequestLine("GET /{stocksTicker}/{date}")
	TickerDto getTicker(@RequestHeader("Authorization") String string, @PathVariable("stocksTicker") String stocksTicker, @PathVariable("date") String date);

}
