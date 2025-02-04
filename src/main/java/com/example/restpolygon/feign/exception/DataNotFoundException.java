package com.example.restpolygon.feign.exception;

public class DataNotFoundException extends RuntimeException {
	public DataNotFoundException(String message) {
		super(message);
	}

}
