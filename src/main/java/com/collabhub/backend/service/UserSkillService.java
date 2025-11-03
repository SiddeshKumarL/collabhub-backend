package com.collabhub.backend.service;

import com.collabhub.backend.model.Skill;
import com.collabhub.backend.model.User;
import com.collabhub.backend.model.UserSkill;
import com.collabhub.backend.repository.SkillRepository;
import com.collabhub.backend.repository.UserRepository;
import com.collabhub.backend.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserSkillService {
    private final UserSkillRepository userSkillRepo;
    private final UserRepository userRepo;
    private final SkillRepository skillRepo;

    public UserSkillService(UserSkillRepository userSkillRepo, UserRepository userRepo, SkillRepository skillRepo) {
        this.userSkillRepo = userSkillRepo;
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
    }

    public List<UserSkill> list(UUID userId) {
        return userSkillRepo.findByUserId(userId);
    }

    public UserSkill add(UUID userId, UUID skillId, String skillType, String proficiency) {
        User user = userRepo.findById(userId).orElseThrow();
        Skill skill = skillRepo.findById(skillId).orElseThrow();

        UserSkill us = new UserSkill();
        us.setId(UUID.randomUUID()); // set id here if your DB doesn’t auto-generate
        us.setUser(user);
        us.setSkill(skill);
        us.setSkillType(skillType);
        us.setProficiency(proficiency);
        us.setCreatedAt(LocalDateTime.now());
        return userSkillRepo.save(us);
    }

    public void remove(UUID id) {
        userSkillRepo.deleteById(id);
    }
}
