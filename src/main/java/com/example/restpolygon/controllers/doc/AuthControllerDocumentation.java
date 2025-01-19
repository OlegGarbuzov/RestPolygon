package com.example.restpolygon.controllers.doc;

import com.example.restpolygon.dto.JwtAuthenticationResponse;
import com.example.restpolygon.dto.SignInRequest;
import com.example.restpolygon.dto.SignUpRequest;
import com.example.restpolygon.error.exception.UserAlreadyExists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface AuthControllerDocumentation {

	@Operation(
			summary = "Регистрация пользователя",
			description = "Этот endpoint используется для регистрации нового пользователя",
			responses =  {
					@ApiResponse(responseCode = "201", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "409", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})

	ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest request) throws UserAlreadyExists;

	@Operation(
			summary = "Авторизация пользователя",
			description = "Этот endpoint используется для авторизации пользователя",
			responses =  {
					@ApiResponse(responseCode = "200", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "403", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})
	ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest request);

}
