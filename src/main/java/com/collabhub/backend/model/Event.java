package com.collabhub.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id; // DB already has UUIDs; do not generate in app

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "external_link")
    private String externalLink; // can be null

    // Postgres array or text; if it's text like "{a,b}", keep as String for now
    private String tags;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
