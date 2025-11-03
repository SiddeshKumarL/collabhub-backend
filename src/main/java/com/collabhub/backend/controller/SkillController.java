package com.collabhub.backend.controller;

import com.collabhub.backend.model.Skill;
import com.collabhub.backend.repository.SkillRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
