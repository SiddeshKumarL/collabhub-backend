package com.collabhub.backend.service;

import com.collabhub.backend.dto.AuthResponse;
import com.collabhub.backend.dto.LoginRequest;
import com.collabhub.backend.dto.RegisterRequest;
import com.collabhub.backend.model.User;
import com.collabhub.backend.repository.UserRepository;
import com.collabhub.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFull_name());
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        user.setPasswordHash(null); // Don't send password in response
        return new AuthResponse(user, token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        user.setPasswordHash(null);
        return new AuthResponse(user, token);
    }

    public User getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPasswordHash(null);
        return user;
    }
}
