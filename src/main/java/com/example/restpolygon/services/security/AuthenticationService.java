package com.example.restpolygon.services.security;

import com.example.restpolygon.client.dto.JwtAuthenticationResponseDto;
import com.example.restpolygon.client.dto.SignInRequestDto;
import com.example.restpolygon.client.dto.SignUpRequestDto;
import com.example.restpolygon.entity.User;
import com.example.restpolygon.enums.Role;
import com.example.restpolygon.error.exception.UserAlreadyExistsException;
import com.example.restpolygon.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public ResponseEntity<JwtAuthenticationResponseDto> signUp(SignUpRequestDto request) throws UserAlreadyExistsException {

		var user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_USER)
				.build();

		userService.create(user);

		var jwt = jwtService.generateAccessToken(user);
		var rJwt = jwtService.generateRefreshToken(user);
		JwtAuthenticationResponseDto jwtAuthenticationResponse = JwtAuthenticationResponseDto.builder()
				.token(jwt)
				.refreshToken(rJwt)
				.build();
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.CREATED);

	}

	public ResponseEntity<JwtAuthenticationResponseDto> signIn(SignInRequestDto request) throws UsernameNotFoundException {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					request.getUsername(),
					request.getPassword()
			));
		} catch (AuthenticationException exception) {
			throw new UsernameNotFoundException(exception.getMessage());
		}

		var user = userService
				.userDetailsService()
				.loadUserByUsername(request.getUsername());

		var jwt = jwtService.generateAccessToken(user);
		var rJwt = jwtService.generateRefreshToken(user);
		JwtAuthenticationResponseDto jwtAuthenticationResponse = JwtAuthenticationResponseDto.builder()
						.token(jwt)
						.refreshToken(rJwt)
				.build();
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.OK);

	}

	public ResponseEntity<JwtAuthenticationResponseDto> refreshToken(String refreshToken) throws UsernameNotFoundException {

		String userName = userService.getByUsername(jwtService.extractRefreshUserName(refreshToken)).getUsername();
		var user = userService
				.userDetailsService()
				.loadUserByUsername(userName);

		var jwt = jwtService.generateAccessToken(user);
		var rJwt = jwtService.generateRefreshToken(user);
		JwtAuthenticationResponseDto jwtAuthenticationResponse = JwtAuthenticationResponseDto.builder()
				.token(jwt)
				.refreshToken(rJwt)
				.build();
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.OK);

	}

}
