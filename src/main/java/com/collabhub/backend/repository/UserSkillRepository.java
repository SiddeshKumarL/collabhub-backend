package com.collabhub.backend.repository;

import com.collabhub.backend.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserSkillRepository extends JpaRepository<UserSkill, UUID> {

    @Query("""
      select us from UserSkill us
      join fetch us.skill s
      where us.user.id = :userId
    """)
    List<UserSkill> findAllByUserIdWithSkill(@Param("userId") UUID userId);

    List<UserSkill> findByUserId(UUID userId);

    boolean existsByUserIdAndSkillId(UUID userId, UUID skillId);
}
