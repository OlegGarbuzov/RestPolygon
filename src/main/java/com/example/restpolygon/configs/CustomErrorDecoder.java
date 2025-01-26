package com.example.restpolygon.configs;

import com.example.restpolygon.error.exception.DataNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode (String methodKey, Response response) {

		FeignException exception = FeignException.errorStatus(methodKey, response);

		int status = response.status();

		if (status >= 500) {
			return new RetryableException(
					response.status(),
					exception.getMessage(),
					response.request().httpMethod(),
					exception,
					100L,
					response.request());
		}

		if (status >= 404) {
			return new DataNotFoundException(exception.getMessage());
		}

		return exception;

	}

}
