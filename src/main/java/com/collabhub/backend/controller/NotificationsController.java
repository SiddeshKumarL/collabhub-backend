package com.collabhub.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:8081")
public class NotificationsController {

    // POST /api/notifications
    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody Map<String, Object> body) {
        // Accept request and return 200 — frontend just expects a 200
        return ResponseEntity.ok().build();
    }
}
