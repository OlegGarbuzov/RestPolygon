package com.example.restpolygon.services;

import com.example.restpolygon.client.dto.TickerDto;
import com.example.restpolygon.dto.JwtAuthenticationResponse;
import com.example.restpolygon.dto.SignInRequest;
import com.example.restpolygon.dto.SignUpRequest;
import com.example.restpolygon.entity.User;
import com.example.restpolygon.enums.Role;

import com.example.restpolygon.error.exception.UserAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest request) throws UserAlreadyExists {

		var user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_USER)
				.build();

		userService.create(user);

		var jwt = jwtService.generateToken(user);
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.CREATED);

	}

	public ResponseEntity<JwtAuthenticationResponse> signIn(SignInRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUsername(),
				request.getPassword()
		));

		var user = userService
				.userDetailsService()
				.loadUserByUsername(request.getUsername());

		var jwt = jwtService.generateToken(user);
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.OK);
	}
}
