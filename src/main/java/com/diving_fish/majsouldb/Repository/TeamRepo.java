package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepo extends JpaRepository<Team, Long> {
    Team findTeamById(Long id);
    Team findTeamByName(String name);
    List<Team> findAllByEnabled(Boolean enabled);
}
