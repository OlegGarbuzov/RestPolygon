package com.example.restpolygon.exceptionHandler;

import com.example.restpolygon.feign.exception.ClientRequestValidationException;
import com.example.restpolygon.feign.exception.DataNotFoundException;
import com.example.restpolygon.feign.exception.TickerCatalogException;
import com.example.restpolygon.user.dto.ErrorResponseDto;
import com.example.restpolygon.user.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
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
	public ResponseEntity<ErrorResponseDto> dataNotFound() {
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(ErrorResponseDto.builder()
						.build());
	}

	@ExceptionHandler(ClientRequestValidationException.class)
	public ResponseEntity<ErrorResponseDto> clientRequestValidation(ClientRequestValidationException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.BAD_REQUEST)
						.errorMessage("Request error: " + exception.getMessage())
						.build());
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponseDto> unAuthorizedRequest(AuthorizationDeniedException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.FORBIDDEN)
						.errorMessage("Unauthorized:" + exception.getMessage())
						.build());
	}

	@ExceptionHandler(TickerCatalogException.class)
	public ResponseEntity<ErrorResponseDto> tickerIsAlreadyExistsInTickerCatalog(TickerCatalogException exception) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.OK)
						.errorMessage(exception.getMessage())
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
