package com.collabhub.backend.repository;

import com.collabhub.backend.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    // Find profile by linked user ID
    Optional<Profile> findByUser_Id(UUID userId);

    // Optionally find profile by email
    Optional<Profile> findByEmail(String email);
}
