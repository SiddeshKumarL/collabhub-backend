package com.collabhub.backend.controller;

import com.collabhub.backend.model.Team;
import com.collabhub.backend.model.User;
import com.collabhub.backend.dto.CreateTeamRequest;
import com.collabhub.backend.repository.TeamRepository;
import com.collabhub.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔹 CREATE TEAM
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB"));

        Team team = new Team();
        team.setName(request.getName());
        team.setTopic(request.getTopic());
        team.setDescription(request.getDescription());
        team.setCapacity(request.getCapacity());
        team.setIsActive(true);
        team.setCreatedAt(LocalDateTime.now());
        team.setOwnerId(owner.getId()); // ✅ Real existing UUID

        Team savedTeam = teamRepository.save(team);
        return ResponseEntity.ok(savedTeam);
    }

    // 🔹 GET ALL TEAMS
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return ResponseEntity.ok(teams);
    }

    // 🔹 GET TEAM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable UUID id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
