package com.example.restpolygon.error.exception;

import com.example.restpolygon.error.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			try (PrintWriter writer = response.getWriter()) {
				ErrorResponseDto  responseDto = ErrorResponseDto.builder()
						.id(UUID.randomUUID())
						.errorCode(HttpStatus.UNAUTHORIZED)
						.errorMessage("Unauthorized")
						.build();
				writer.print(objectMapper.writeValueAsString(responseDto));
			}

	}
}
