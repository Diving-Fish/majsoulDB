package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Ready;
import com.diving_fish.majsouldb.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadyRepo extends JpaRepository<Ready, Long> {
    Ready findByTeamIdAndRound(Long teamId, Long round);
}
