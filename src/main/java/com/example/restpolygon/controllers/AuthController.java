package com.example.restpolygon.controllers;

import com.example.restpolygon.Dto.JwtAuthenticationResponse;
import com.example.restpolygon.Dto.SignInRequest;
import com.example.restpolygon.Dto.SignUpRequest;
import com.example.restpolygon.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationService authenticationService;

	@Operation(summary = "Регистрация пользователя")
	@PostMapping("/register")
	public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request) {
		return authenticationService.signUp(request);
	}

	@Operation(summary = "Авторизация пользователя")
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
		return authenticationService.signIn(request);
	}
}
