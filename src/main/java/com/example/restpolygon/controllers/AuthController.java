package com.example.restpolygon.controllers;

import com.example.restpolygon.client.dto.JwtAuthenticationResponseDto;
import com.example.restpolygon.client.dto.SignInRequestDto;
import com.example.restpolygon.client.dto.SignUpRequestDto;
import com.example.restpolygon.controllers.doc.AuthControllerDocumentation;
import com.example.restpolygon.error.exception.UserAlreadyExistsException;
import com.example.restpolygon.services.security.AuthenticationService;
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
public class AuthController implements AuthControllerDocumentation {

	private final AuthenticationService authenticationService;


	@PostMapping("/register")
	public ResponseEntity<JwtAuthenticationResponseDto> signUp(@RequestBody @Valid SignUpRequestDto request) throws UserAlreadyExistsException {
		return authenticationService.signUp(request);
	}


	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponseDto> signIn(@RequestBody @Valid SignInRequestDto request) {
		return authenticationService.signIn(request);
	}
}
