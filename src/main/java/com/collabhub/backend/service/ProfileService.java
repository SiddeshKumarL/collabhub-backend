package com.collabhub.backend.service;

import com.collabhub.backend.model.Profile;
import com.collabhub.backend.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // 🔹 Get profile by its primary ID
    public Profile getProfile(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
    }

    // 🔹 Update profile
    public Profile updateProfile(UUID id, Profile updatedProfile) {
        Profile existingProfile = getProfile(id);

        // Map only the updatable fields
        existingProfile.setFullName(updatedProfile.getFullName());
        existingProfile.setBio(updatedProfile.getBio());
        existingProfile.setEmail(updatedProfile.getEmail());

        return profileRepository.save(existingProfile);
    }

    // 🔹 Get profile by User ID
    public Profile getProfileByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for user ID: " + userId));
    }
}
