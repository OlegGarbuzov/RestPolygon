package com.example.restpolygon.feign.exception;

public class ClientRequestValidationException extends IllegalArgumentException {
	public ClientRequestValidationException(String message) {
		super(message);
	}

}
