package com.example.restpolygon.user.security;

import com.example.restpolygon.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	@Value("${token.access.key}")
	private String jwtAccessKey;
	@Value("${token.refresh.key}")
	private String jwtRefreshKey;

	public String extractAccessUserName(String token) {
		return extractClaim(token, Claims::getSubject, jwtAccessKey);
	}

	public String extractRefreshUserName(String token) {
		return extractClaim(token, Claims::getSubject, jwtRefreshKey);
	}

	public String generateAccessToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		if (userDetails instanceof User customUserDetails) {
			claims.put("id", customUserDetails.getId());
			claims.put("email", customUserDetails.getEmail());
			claims.put("role", customUserDetails.getRole());
		}
		return generateAccessToken(claims, userDetails);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		if (userDetails instanceof User customUserDetails) {
			claims.put("id", customUserDetails.getId());
			claims.put("email", customUserDetails.getEmail());
			claims.put("role", customUserDetails.getRole());
		}
		return generateRefreshToken(claims, userDetails);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractAccessUserName(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers, String jwtKey) {
		final Claims claims = extractAllClaims(token, jwtKey);
		return claimsResolvers.apply(claims);
	}

	private String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 100000 * 60))
				.signWith(getKey(jwtAccessKey), Jwts.SIG.HS256).compact();
	}

	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 240))
				.signWith(getKey(jwtRefreshKey), Jwts.SIG.HS256).compact();
	}


	private boolean isTokenExpired(String token) {
		return extractExpiration(token, jwtAccessKey).before(new Date());
	}

	private Date extractExpiration(String token, String jwtKey) {
		return extractClaim(token, Claims::getExpiration, jwtKey);
	}

	private Claims extractAllClaims(String token, String jwtKey) {
		return Jwts.parser()
				.verifyWith(getKey(jwtKey))
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private SecretKey getKey(String jwtKey) {
		byte[] keyBytes = Decoders.BASE64.decode(jwtKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}


}
