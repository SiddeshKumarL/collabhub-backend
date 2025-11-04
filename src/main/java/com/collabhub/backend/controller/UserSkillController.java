package com.collabhub.backend.controller;

import com.collabhub.backend.model.UserSkill;
import com.collabhub.backend.service.UserSkillService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-skills")
@CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")

public class UserSkillController {

    private final UserSkillService service;

    public UserSkillController(UserSkillService service) {
        this.service = service;
    }

    // GET /api/user-skills/me?userId=...
    @GetMapping("/me")
    public List<UserSkill> getMySkills(@RequestParam UUID userId) {
        return service.list(userId);
    }

    // Or keep path param if you prefer:
    // @GetMapping("/{userId}")
    // public List<UserSkill> getUserSkills(@PathVariable UUID userId) {
    //     return service.list(userId);
    // }

    // POST /api/user-skills
    @PostMapping
    public UserSkill addUserSkill(@RequestBody AddUserSkillDto dto) {
        return service.add(dto.userId(), dto.skillId(), dto.skillType(), dto.proficiency());
    }

    // DELETE /api/user-skills/{id}
    @DeleteMapping("/{id}")
    public void remove(@PathVariable UUID id) {
        service.remove(id);
    }

    public record AddUserSkillDto(UUID userId, UUID skillId, String skillType, String proficiency) {}
}
