package com.collabhub.backend.controller;

import com.collabhub.backend.dto.AuthResponse;
import com.collabhub.backend.dto.LoginRequest;
import com.collabhub.backend.dto.RegisterRequest;
import com.collabhub.backend.model.User;
import com.collabhub.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(authService.getCurrentUser(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword() {
        // Implement password reset logic
        return ResponseEntity.ok().build();
    }
}
