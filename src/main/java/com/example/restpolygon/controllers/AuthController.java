package com.example.restpolygon.controllers;

import com.example.restpolygon.dto.JwtAuthenticationResponse;
import com.example.restpolygon.dto.SignInRequest;
import com.example.restpolygon.dto.SignUpRequest;
import com.example.restpolygon.controllers.doc.AuthControllerDocumentation;
import com.example.restpolygon.error.exception.UserAlreadyExists;
import com.example.restpolygon.services.AuthenticationService;
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
	public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) throws UserAlreadyExists {
		return authenticationService.signUp(request);
	}


	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
		return authenticationService.signIn(request);
	}
}
