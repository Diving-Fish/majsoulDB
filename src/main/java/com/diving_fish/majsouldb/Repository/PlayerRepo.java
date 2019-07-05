package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepo extends JpaRepository<Player, Long> {
    Player findPlayerById(Long id);
    Player findPlayerByName(String name);
    List<Player> findPlayersByTeamId(Long teamId);
}
