package com.auth.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.api.dto.LoginRequest;
import com.auth.api.dto.LoginResponse;
import com.auth.config.JwtUtil;

@RestController
public class AuthController {

    private final JwtUtil jwtUtil;

    // Usuarios hardcodeados: username -> {password, id, roles}
    private static final Map<String, Object[]> USERS = Map.of(
        "admin",    new Object[]{"12345678", 1L, List.of("ADMIN")},
        "customer", new Object[]{"12345678", 2L, List.of("CUSTOMER")}
    );

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Object[] user = USERS.get(request.getUsername());

        if (user == null || !user[0].equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        String username = request.getUsername();
        Long id = (Long) user[1];
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) user[2];

        String token = jwtUtil.generateToken(username, id, roles);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
