package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepo extends JpaRepository<Match, Long> {
    List<Match> findAllByTeamId1AndTeamId2AndTeamId3AndTeamId4AndRound(Long id1, Long id2, Long id3, Long id4, Integer round);
}
