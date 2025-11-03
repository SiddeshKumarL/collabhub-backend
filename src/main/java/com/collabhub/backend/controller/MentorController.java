package com.collabhub.backend.controller;

import com.collabhub.backend.model.User;
import com.collabhub.backend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@CrossOrigin(origins = "http://localhost:8081")
public class MentorController {

    private final UserRepository userRepository;

    public MentorController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /api/mentors -> return all users with role "MENTOR"
    @GetMapping
    public List<User> getMentors() {
        return userRepository.findByRole("MENTOR");
    }
}
