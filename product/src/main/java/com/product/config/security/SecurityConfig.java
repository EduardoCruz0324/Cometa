package com.product.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.product.config.jwt.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/error").permitAll()

                // Customer: consultar categorías activas
                .requestMatchers(HttpMethod.GET, "/category/active").hasAnyAuthority("ADMIN", "CUSTOMER")

                // Admin: acceso a todos los endpoints de categoría
                .requestMatchers(HttpMethod.GET, "/category", "/category/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/category").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/category/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/category/**").hasAuthority("ADMIN")

                // Customer: consultar detalle de producto e imágenes
                .requestMatchers(HttpMethod.GET, "/product/{id}").hasAnyAuthority("ADMIN", "CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/product/{id}/image").hasAnyAuthority("ADMIN", "CUSTOMER")

                // Admin: acceso a todos los endpoints de producto
                .requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
            )
            .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
