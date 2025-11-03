package com.collabhub.backend.service;

import com.collabhub.backend.model.Course;
import com.collabhub.backend.model.Skill;
import com.collabhub.backend.model.UserSkill;
import com.collabhub.backend.repository.CourseRepository;
import com.collabhub.backend.repository.SkillRepository;
import com.collabhub.backend.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppService {

    private final CourseRepository courseRepository;
    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;

    public AppService(CourseRepository courseRepository,
                      SkillRepository skillRepository,
                      UserSkillRepository userSkillRepository) {
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
    }

    // Fetch all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Fetch all skills
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // Recommend courses based on user skills
    public List<Course> recommendCoursesForUser(UUID userId) {
        if (userId == null) {
            return Collections.emptyList();
        }

        List<UserSkill> userSkills = Optional.ofNullable(
                userSkillRepository.findByUserId(userId)
        ).orElse(Collections.emptyList());

        Set<String> skillNames = userSkills.stream()
                .filter(us -> us.getSkill() != null && us.getSkill().getName() != null)
                .map(us -> us.getSkill().getName().toLowerCase())
                .collect(Collectors.toSet());

        List<Course> allCourses = Optional.ofNullable(
                courseRepository.findAll()
        ).orElse(Collections.emptyList());

        List<Course> matched = allCourses.stream()
                .filter(c -> c.getSkill() != null && c.getSkill().getName() != null)
                .filter(c -> skillNames.contains(c.getSkill().getName().toLowerCase()))
                .limit(5)
                .collect(Collectors.toList());

        // If no direct match, return random 5
        if (matched.isEmpty()) {
            Collections.shuffle(allCourses);
            matched = allCourses.stream().limit(5).collect(Collectors.toList());
        }

        return matched;
    }
}
