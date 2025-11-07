package com.collabhub.backend.controller;

import com.collabhub.backend.model.TeamMember;
import com.collabhub.backend.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/team-members")
@CrossOrigin(origins = "http://localhost:8081")
public class TeamMemberController {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @GetMapping
    public List<TeamMember> getByTeam(@RequestParam UUID teamId) {
        return teamMemberRepository.findByTeamId(teamId);
    }

    @PostMapping
    public TeamMember applyToTeam(@RequestBody TeamMember member) {
        member.setId(null);
        member.setStatus(TeamMember.Status.PENDING);
        return teamMemberRepository.save(member);
    }

    @PatchMapping("/{id}")
    public TeamMember updateStatus(@PathVariable UUID id, @RequestBody TeamMember partial) {
        return teamMemberRepository.findById(id).map(member -> {
            if (partial.getStatus() != null) {
                member.setStatus(partial.getStatus());
            }
            return teamMemberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }
}
