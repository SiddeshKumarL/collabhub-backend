package com.collabhub.backend.repository;

import com.collabhub.backend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
