package com.collabhub.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_skills")
public class UserSkill {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id; // remove @GeneratedValue if rows are pre-seeded with UUIDs

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // ensure User entity uses @Id UUID and @Table(name="users")

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill; // your Skill entity already mapped

    @Column(name = "skill_type")
    private String skillType; // e.g., TECHNICAL | SOFT | TOOL

    private String proficiency; // e.g., BEGINNER | INTERMEDIATE | ADVANCED

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
