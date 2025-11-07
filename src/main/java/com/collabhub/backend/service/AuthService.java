package com.collabhub.backend.service;

import com.collabhub.backend.dto.AuthResponse;
import com.collabhub.backend.dto.LoginRequest;
import com.collabhub.backend.dto.RegisterRequest;
import com.collabhub.backend.model.Profile;
import com.collabhub.backend.model.User;
import com.collabhub.backend.repository.ProfileRepository;
import com.collabhub.backend.repository.UserRepository;
import com.collabhub.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔹 Register user and auto-create blank profile
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create and save user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFull_name());
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        // Auto-create a blank profile linked to the user
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFullName(user.getFullName());
        profile.setEmail(user.getEmail());
        profile.setBio("This is your bio — update it anytime!");
        profile.setLinkedinUrl(null);
        profile.setGithubUrl(null);
        profile.setAvatarUrl(null);
        profileRepository.save(profile);

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail());

        // Hide sensitive info
        user.setPasswordHash(null);

        return new AuthResponse(user, token);
    }

    // 🔹 Login
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

    // 🔹 Get current user
    public User getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPasswordHash(null);
        return user;
    }
}
