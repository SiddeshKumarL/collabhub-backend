package com.collabhub.backend.service;

import com.collabhub.backend.model.Team;
import com.collabhub.backend.repository.TeamRepository;
import org.springframework.stereotype.Service;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // ✅ Get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // ✅ Get team by ID
    public Team getTeamById(UUID id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    // ✅ Create new team
    public Team createTeam(Team team) {
        // Ensure isActive defaults to true if not set
        if (team.getIsActive() == null) {
            team.setIsActive(true);
        }
        return teamRepository.save(team);
    }

    // ✅ Update existing team
    public Team updateTeam(UUID id, Team teamDetails) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        existingTeam.setName(teamDetails.getName());
        existingTeam.setTopic(teamDetails.getTopic());
        existingTeam.setDescription(teamDetails.getDescription());
        existingTeam.setCapacity(teamDetails.getCapacity());
        existingTeam.setIsActive(teamDetails.getIsActive());

        return teamRepository.save(existingTeam);
    }

    // ✅ Delete team
    public void deleteTeam(UUID id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }
}
