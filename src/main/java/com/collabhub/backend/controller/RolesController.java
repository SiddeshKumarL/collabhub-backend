package com.collabhub.backend.controller;

import com.collabhub.backend.model.User;
import com.collabhub.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:8081")
public class RolesController {

    private final UserRepository userRepository;

    public RolesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /api/roles/{userId} -> returns an object { "role": "MENTOR" } or { "role": null }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getRole(@PathVariable UUID userId) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        User user = opt.get();
        return ResponseEntity.ok().body(java.util.Map.of("role", user.getRole()));
    }

    // POST /api/roles/{userId}?role=MENTOR (activate role)
    @PostMapping("/{userId}")
    public ResponseEntity<?> addRole(@PathVariable UUID userId, @RequestParam String role) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        User user = opt.get();
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/roles/{userId}?role=MENTOR (remove role)
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeRole(@PathVariable UUID userId, @RequestParam String role) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        User user = opt.get();
        // If role matches, remove; otherwise no-op
        if (role.equalsIgnoreCase(user.getRole())) {
            user.setRole(null);
            userRepository.save(user);
        }
        return ResponseEntity.ok().build();
    }
}
