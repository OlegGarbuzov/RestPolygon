package com.example.restpolygon.user.security;

import com.example.restpolygon.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Value("${client.integration.serviceAuthorizationPrefix}")
	private String BEARER_PREFIX;
	private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
	private final JwtService jwtService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {

		var authHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		var jwt = authHeader.substring(BEARER_PREFIX.length());
		var username = jwtService.extractAccessUserName(jwt);

		if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(username);

			if (jwtService.isTokenValid(jwt, userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
				);

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}
		}
		filterChain.doFilter(request, response);
	}
}
