package com.collabhub.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@Table(name = "skills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Skill {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id; // Existing UUIDs in DB; do NOT use @GeneratedValue

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "skill_difficulty")
    private String difficulty;

    @Column(name = "mindmap_url")
    private String mindmapUrl;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // One skill can belong to multiple users (through user_skills)
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("skill") // prevent infinite recursion
    private List<UserSkill> userSkills;
}
