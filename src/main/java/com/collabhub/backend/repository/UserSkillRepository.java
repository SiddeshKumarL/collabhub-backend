package com.collabhub.backend.repository;

import com.collabhub.backend.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserSkillRepository extends JpaRepository<UserSkill, UUID> {
    List<UserSkill> findByUserId(UUID userId);
    boolean existsByUserIdAndSkillId(UUID userId, UUID skillId);
}
