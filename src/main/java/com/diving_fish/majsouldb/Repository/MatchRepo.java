package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepo extends JpaRepository<Match, Long> {
    List<Match> findAllByTeamIdAndRound(Long team_id, Integer round);
}
