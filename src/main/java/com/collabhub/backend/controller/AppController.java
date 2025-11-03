package com.collabhub.backend.controller;

import com.collabhub.backend.model.*;
import com.collabhub.backend.service.AppService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/app")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }





    @GetMapping("/recommendations/{userId}")
    public List<Course> recommendCourses(@PathVariable UUID userId) {
        return appService.recommendCoursesForUser(userId);
    }
}
