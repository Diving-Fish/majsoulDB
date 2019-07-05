package com.diving_fish.majsouldb.Repository;

import com.diving_fish.majsouldb.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepo extends JpaRepository<Group, Long> {
    List<Group> findAllByRound(Long round);
}
