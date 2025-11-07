package com.collabhub.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "profiles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column(length = 1000)
    private String bio = "This is your bio — update it anytime!";

    private String linkedinUrl;
    private String githubUrl;
    private String avatarUrl;
    private String email;
    private boolean mentor = false;

    // Constructors
    public Profile() {}

    public Profile(User user) {
        this.user = user;
        this.fullName = user.getFullName();
        this.email = user.getEmail();
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.fullName = user.getFullName();
            this.email = user.getEmail();
        }
    }
}
