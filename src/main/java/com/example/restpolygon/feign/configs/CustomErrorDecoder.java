package com.example.restpolygon.feign.configs;

import com.example.restpolygon.feign.exception.DataNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

	private static final Integer[] RETRYABLE_HTTP_CODES = {
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			HttpStatus.SERVICE_UNAVAILABLE.value(),
			HttpStatus.GATEWAY_TIMEOUT.value(),
			HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value()
	};

	private static final Integer[] NON_RETRYABLE_HTTP_CODES = {
			HttpStatus.NOT_FOUND.value(),
	};

	private static final Long RETRY_TIMEOUT = 100L;

	@Override
	public Exception decode (String methodKey, Response response) {

		FeignException exception = FeignException.errorStatus(methodKey, response);

		int status = response.status();

		if (Arrays.asList(RETRYABLE_HTTP_CODES).contains(status)) {
			return new RetryableException(
					response.status(),
					exception.getMessage(),
					response.request().httpMethod(),
					exception,
					RETRY_TIMEOUT,
					response.request());
		}

		if (Arrays.asList(NON_RETRYABLE_HTTP_CODES).contains(status)) {
			return new DataNotFoundException(exception.getMessage());
		}

		return exception;

	}

}
