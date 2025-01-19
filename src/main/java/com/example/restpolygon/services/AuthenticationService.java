package com.example.restpolygon.services;

import com.example.restpolygon.Dto.JwtAuthenticationResponse;
import com.example.restpolygon.Dto.SignInRequest;
import com.example.restpolygon.Dto.SignUpRequest;
import com.example.restpolygon.entity.User;
import com.example.restpolygon.enums.Role;
import com.example.restpolygon.exceptions.UserAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	/**
	 * Регистрация пользователя
	 *
	 * @param request данные пользователя
	 * @return токен
	 */
	public ResponseEntity<Object> signUp(SignUpRequest request) {

		var user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_USER)
				.build();

		try {
			userService.create(user);
		} catch (UserAlreadyExists re) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(re.getMessage());
		}


		var jwt = jwtService.generateToken(user);
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
		return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.CREATED);

	}

	/**
	 * Аутентификация пользователя
	 *
	 * @param request данные пользователя
	 * @return токен
	 */
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
