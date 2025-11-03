package com.collabhub.backend.controller;

import com.collabhub.backend.model.Course;
import com.collabhub.backend.service.AppService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final AppService appService;

    public CourseController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return appService.getAllCourses();
    }

    @GetMapping("/recommend/{userId}")
    public List<Course> recommendCourses(@PathVariable UUID userId) {
        return appService.recommendCoursesForUser(userId);
    }
}
