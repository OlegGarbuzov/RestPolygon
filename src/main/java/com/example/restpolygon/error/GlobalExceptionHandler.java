package com.example.restpolygon.error;

import com.example.restpolygon.error.dto.ErrorResponseDto;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.error.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;


@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> userAlreadyExists(UserAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.CONFLICT)
						.errorMessage(exception.getMessage())
						.build());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> userNotFound(UsernameNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.NOT_FOUND)
						.errorMessage(exception.getMessage())
						.build());
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> dataNotFound(DataNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.NOT_FOUND)
						.errorMessage("Request error:" + exception.getMessage())
						.build());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> global(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
						.errorMessage("Internal server error:" + exception.getMessage())
						.build());
	}

}
