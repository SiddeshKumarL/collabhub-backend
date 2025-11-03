package com.collabhub.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "skills")
public class Skill {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id; // Existing UUIDs in DB; do NOT use @GeneratedValue

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // In DB it appears as "skill_difficulty"
    @Column(name = "skill_difficulty")
    private String difficulty;

    // In DB it appears as "pdf_url"
    @Column(name = "pdf_url")
    private String pdfurl;

    // In DB it appears as "mindmap_url"
    @Column(name = "mindmap_url")
    private String mindmap;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> userSkills;
}
