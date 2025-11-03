package com.collabhub.backend.controller;

import com.collabhub.backend.model.Course;
import com.collabhub.backend.model.User;
import com.collabhub.backend.repository.CourseRepository;
import com.collabhub.backend.repository.UserSkillRepository;
import com.collabhub.backend.repository.UserRepository;
import com.collabhub.backend.service.AppService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:8081")
public class DashboardController {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final AppService appService;
    private final CourseRepository courseRepository;

    public DashboardController(UserRepository userRepository,
                               UserSkillRepository userSkillRepository,
                               AppService appService,
                               CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
        this.appService = appService;
        this.courseRepository = courseRepository;
    }

    // GET /api/dashboard/stats/{userId}
    @GetMapping("/stats/{userId}")
    public Map<String, Object> stats(@PathVariable UUID userId) {
        long teachingSkills = userSkillRepository.findByUserId(userId).stream()
                .filter(us -> "TEACH".equalsIgnoreCase(us.getSkillType())).count();
        long learningSkills = userSkillRepository.findByUserId(userId).stream()
                .filter(us -> "LEARN".equalsIgnoreCase(us.getSkillType())).count();

        return Map.of(
                "teachingSkills", teachingSkills,
                "learningSkills", learningSkills,
                "teams", 0,
                "events", 0
        );
    }

    // GET /api/dashboard/recommendations/{userId}
    @GetMapping("/recommendations/{userId}")
    public List<Course> recommendations(@PathVariable UUID userId) {
        return appService.recommendCoursesForUser(userId);
    }
}
