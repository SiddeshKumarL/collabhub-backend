package com.collabhub.backend.service;

import com.collabhub.backend.model.Course;
import com.collabhub.backend.model.UserSkill;
import com.collabhub.backend.repository.CourseRepository;
import com.collabhub.backend.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserSkillRepository userSkillRepository;

    public CourseService(CourseRepository courseRepository,
                         UserSkillRepository userSkillRepository) {
        this.courseRepository = courseRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> recommendCoursesForUser(UUID userId) {
        List<UserSkill> userSkills = Optional.ofNullable(
                userSkillRepository.findByUserId(userId)
        ).orElse(Collections.emptyList());

        Set<String> skillNames = userSkills.stream()
                .filter(us -> us.getSkill() != null)
                .map(us -> us.getSkill().getName().toLowerCase())
                .collect(Collectors.toSet());

        List<Course> allCourses = courseRepository.findAll();

        List<Course> matched = allCourses.stream()
                .filter(c -> c.getSkill() != null &&
                        skillNames.contains(c.getSkill().getName().toLowerCase()))
                .limit(5)
                .collect(Collectors.toList());

        if (matched.isEmpty()) {
            Collections.shuffle(allCourses);
            matched = allCourses.stream().limit(5).collect(Collectors.toList());
        }

        return matched;
    }
}
