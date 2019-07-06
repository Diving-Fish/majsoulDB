package com.diving_fish.majsouldb.Entity;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer round;

    @Column
    private Integer position;

    @Column
    private Long teamPlayer;

    @Column
    private Long teamId;

    @Column
    private Long teamScore;

    public Match(Integer round, Integer position, Long teamPlayer, Long teamId, Long teamScore) {
        this.round = round;
        this.position = position;
        this.teamPlayer = teamPlayer;
        this.teamId = teamId;
        this.teamScore = teamScore;
    }

    public Match() {

    }
}
