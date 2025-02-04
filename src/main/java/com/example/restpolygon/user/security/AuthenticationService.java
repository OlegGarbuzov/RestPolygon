package com.example.restpolygon.user.security;

import com.example.restpolygon.user.dto.JwtAuthenticationResponseDto;
import com.example.restpolygon.user.dto.SignInRequestDto;
import com.example.restpolygon.user.dto.SignUpRequestDto;
import com.example.restpolygon.user.entity.User;
import com.example.restpolygon.user.enums.Role;
import com.example.restpolygon.user.service.UserService;
import lombok.RequiredArgsConstructor;
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

	public JwtAuthenticationResponseDto signUp(SignUpRequestDto request) {
		var user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_USER)
				.build();

		userService.create(user);

		var jwt = jwtService.generateAccessToken(user);
		var rJwt = jwtService.generateRefreshToken(user);

		return JwtAuthenticationResponseDto.builder()
				.token(jwt)
				.refreshToken(rJwt)
				.build();
	}

	public JwtAuthenticationResponseDto signIn(SignInRequestDto request) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					request.getUsername(),
					request.getPassword()
			));
		} catch (AuthenticationException exception) {
			throw new UsernameNotFoundException(exception.getMessage());
		}

		var user = userService.loadUserByUsername(request.getUsername());

		var jwt = jwtService.generateAccessToken(user);
		var rJwt = jwtService.generateRefreshToken(user);
		return JwtAuthenticationResponseDto.builder()
				.token(jwt)
				.refreshToken(rJwt)
				.build();
	}

	public JwtAuthenticationResponseDto refreshToken(String refreshToken) {
		String userName = userService.loadUserByUsername(jwtService.extractRefreshUserName(refreshToken)).getUsername();
		var user = userService.loadUserByUsername(userName);

		var jwt = jwtService.generateAccessToken(user);
		var rJwt = jwtService.generateRefreshToken(user);
		return JwtAuthenticationResponseDto.builder()
				.token(jwt)
				.refreshToken(rJwt)
				.build();
	}

}
