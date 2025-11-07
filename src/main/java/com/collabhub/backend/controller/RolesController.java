package com.collabhub.backend.controller;

import com.collabhub.backend.model.User;
import com.collabhub.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

    // GET /api/roles/{userId} -> returns { "role": "MENTOR" } or { "role": null }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getRole(@PathVariable UUID userId) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        User user = opt.get();
        // Use Map.of(...) only when non-null, else fallback to singletonMap (allows null)
        String role = user.getRole();
        return ResponseEntity.ok(Map.of("role", role == null ? "" : role));
    }

    // POST /api/roles/{userId}?role=MENTOR
    @PostMapping("/{userId}")
    public ResponseEntity<?> addRole(@PathVariable UUID userId, @RequestParam String role) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        User user = opt.get();
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Role added successfully"));
    }

    // DELETE /api/roles/{userId}?role=MENTOR
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> removeRole(@PathVariable UUID userId, @RequestParam String role) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        User user = opt.get();
        if (role.equalsIgnoreCase(user.getRole())) {
            user.setRole(null);
            userRepository.save(user);
        }

        return ResponseEntity.ok(Map.of("message", "Role removed successfully"));
    }
}
