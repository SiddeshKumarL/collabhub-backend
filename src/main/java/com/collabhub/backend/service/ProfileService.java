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

    public Profile getProfile(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public Profile updateProfile(UUID id, Profile updatedProfile) {
        Profile existingProfile = getProfile(id);
        existingProfile.setFullName(updatedProfile.getFullName());
        existingProfile.setBio(updatedProfile.getBio());
        existingProfile.setLinkedinUrl(updatedProfile.getLinkedinUrl());
        existingProfile.setGithubUrl(updatedProfile.getGithubUrl());
        existingProfile.setAvatarUrl(updatedProfile.getAvatarUrl());
        return profileRepository.save(existingProfile);
    }

    public Profile getProfileByUserId(UUID userId) {
        return profileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for user ID: " + userId));
    }
}
