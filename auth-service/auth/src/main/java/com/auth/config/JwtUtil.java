package com.auth.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Misma clave secreta que usa el product-service para validar
    private final String secretKey =
            "mi_clave_super_secreta_para_jwt_segura_2026_abcdef";
    private final SecretKey signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // Genera un token JWT con username, id y roles
    public String generateToken(String username, Long id, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
