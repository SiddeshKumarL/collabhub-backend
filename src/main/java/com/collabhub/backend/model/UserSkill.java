package com.collabhub.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@Table(name = "user_skills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserSkill {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id; // remove @GeneratedValue if your DB already has UUIDs

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("userSkills") // stop loop back to user
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    @JsonIgnoreProperties("userSkills") // stop loop back to skill
    private Skill skill;

    @Column(name = "skill_type")
    private String skillType; // e.g., TECHNICAL | SOFT | TOOL

    private String proficiency; // e.g., BEGINNER | INTERMEDIATE | ADVANCED

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
