package com.example.restpolygon.controllers;

import com.example.restpolygon.controllers.doc.AuthControllerDocumentation;
import com.example.restpolygon.user.dto.JwtAuthenticationResponseDto;
import com.example.restpolygon.user.dto.SignInRequestDto;
import com.example.restpolygon.user.dto.SignUpRequestDto;
import com.example.restpolygon.user.security.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocumentation {

	private final AuthenticationService authenticationService;

	@Value("${client.integration.serviceAuthorizationPrefix}")
	private String BEARER_PREFIX;


	@PostMapping("/register")
	public ResponseEntity<JwtAuthenticationResponseDto> signUp(@RequestBody @Valid SignUpRequestDto request) {
		JwtAuthenticationResponseDto jwtAuthenticationResponseDto =  authenticationService.signUp(request);
		return new ResponseEntity<>(jwtAuthenticationResponseDto, HttpStatus.CREATED);
	}


	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponseDto> signIn(@RequestBody @Valid SignInRequestDto request) {
		JwtAuthenticationResponseDto jwtAuthenticationResponseDto = authenticationService.signIn(request);
		return new ResponseEntity<>(jwtAuthenticationResponseDto, HttpStatus.OK);
	}

	@GetMapping("/refreshToken")
	public ResponseEntity<JwtAuthenticationResponseDto> refreshToken(HttpServletRequest request) {
		String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
		String refreshToken = headerAuth.substring(BEARER_PREFIX.length());

		JwtAuthenticationResponseDto jwtAuthenticationResponse = authenticationService.refreshToken(refreshToken);
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.OK);
	}

}
