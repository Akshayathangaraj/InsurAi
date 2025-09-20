package com.insurai.insurai.config;

import com.insurai.insurai.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // 🔑 Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ⚡ AuthenticationManager (needed for login/auth)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 🔒 Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())   // Disable CSRF for frontend
            .cors(cors -> {})               // Enable CORS
            .authorizeHttpRequests(auth -> auth
                // ✅ Public APIs (no auth needed)
                .requestMatchers("/api/auth/**").permitAll()        // Employee login/signup
                .requestMatchers("/api/admin/auth/**").permitAll()  // Admin login/signup
                .requestMatchers("/api/employees/**").permitAll()   // Employees list
                .requestMatchers("/api/insurance/**").permitAll()   // Insurance list
                .requestMatchers("/api/upload/**").permitAll()      // File upload
                .requestMatchers("/uploads/**").permitAll()         // Uploaded images

                // 🛡 Admin-only APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // 🔐 Everything else requires authentication
                .anyRequest().authenticated()
            )
            // Add JWT filter before Spring’s default authentication filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
