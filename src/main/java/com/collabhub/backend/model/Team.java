package com.collabhub.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Data
@Entity
@Table(name = "teams")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class) // 🔹 Fix JSON key naming
public class Team {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String topic;

    private String description;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false, name = "ownerId")
    private UUID ownerId; // ✅ keep camelCase here

    @Column(nullable = false, name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
