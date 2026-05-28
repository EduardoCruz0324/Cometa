package com.invoice.config.jwt;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String secretKey = "mi_clave_super_secreta_para_jwt_segura_2026_abcdef";
	private final SecretKey signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

	public Claims extractClaims(String token) {
		JwtParser jwtParser = Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build();
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Long extractUserId(String token) {
		Object idClaim = extractClaims(token).get("id");
		if (idClaim instanceof Number number) {
			return number.longValue();
		}
		return null;
	}

	public List<String> extractPermisos(String token) {
		Object rolesClaim = extractClaims(token).get("roles");
		if (!(rolesClaim instanceof List<?> roles)) {
			return List.of();
		}
		return roles.stream()
				.map(this::extractAuthority)
				.filter(Objects::nonNull)
				.toList();
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		return claimsResolver.apply(extractClaims(token));
	}

	private String extractAuthority(Object role) {
		if (role instanceof String authority) {
			return authority;
		}
		if (role instanceof Map<?, ?> roleMap) {
			Object authority = roleMap.get("authority");
			if (authority instanceof String authorityValue) {
				return authorityValue;
			}
		}
		return null;
	}
}
