package com.collabhub.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/api/follows")
@CrossOrigin(origins = "http://localhost:8081")
public class FollowsController {

    // in-memory follow store: followerId -> set of followed userIds
    private final Map<UUID, Set<UUID>> follows = new ConcurrentHashMap<>();

    // GET /api/follows/{userId} -> return array of ids this user follows
    @GetMapping("/{userId}")
    public ResponseEntity<List<String>> getFollows(@PathVariable UUID userId) {
        Set<UUID> set = follows.getOrDefault(userId, Collections.emptySet());
        List<String> result = set.stream().map(UUID::toString).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // POST /api/follows  { followerId, followeeId }
    @PostMapping
    public ResponseEntity<?> addFollow(@RequestBody Map<String, String> body) {
        try {
            UUID from = UUID.fromString(body.get("followerId"));
            UUID to = UUID.fromString(body.get("followeeId"));
            follows.computeIfAbsent(from, k -> ConcurrentHashMap.newKeySet()).add(to);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid body"));
        }
    }

    // DELETE /api/follows  { followerId, followeeId }
    @DeleteMapping
    public ResponseEntity<?> removeFollow(@RequestBody Map<String, String> body) {
        try {
            UUID from = UUID.fromString(body.get("followerId"));
            UUID to = UUID.fromString(body.get("followeeId"));
            Set<UUID> set = follows.getOrDefault(from, Collections.emptySet());
            set.remove(to);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid body"));
        }
    }
}
