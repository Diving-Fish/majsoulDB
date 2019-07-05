package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<Team, Long> {
    Team findTeamById(Long id);
    Team findTeamByName(String name);
}
