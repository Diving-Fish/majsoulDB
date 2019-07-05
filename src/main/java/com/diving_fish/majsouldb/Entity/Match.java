package com.diving_fish.majsouldb.Entity;

import net.sf.json.JSONArray;

import javax.persistence.*;
import java.util.List;

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
    private Long teamPlayer1;

    @Column
    private Long teamId1;

    @Column
    private Long teamScore1;

    @Column
    private Long teamPlayer2;

    @Column
    private Long teamId2;

    @Column
    private Long teamScore2;

    @Column
    private Long teamPlayer3;

    @Column
    private Long teamId3;

    @Column
    private Long teamScore3;

    @Column
    private Long teamPlayer4;

    @Column
    private Long teamId4;

    @Column
    private Long teamScore4;

    public Match() {

    }

    public Match(Integer round, Integer position, JSONArray teamIds, JSONArray playerIds, JSONArray scores) {
        this.round = round;
        this.position = position;
        this.teamPlayer1 = playerIds.getLong(0);
        this.teamId1 = teamIds.getLong(0);
        this.teamScore1 = scores.getLong(0);
        this.teamPlayer2 = playerIds.getLong(1);
        this.teamId2 = teamIds.getLong(1);
        this.teamScore2 = scores.getLong(1);
        this.teamPlayer3 = playerIds.getLong(2);
        this.teamId3 = teamIds.getLong(2);
        this.teamScore3 = scores.getLong(2);
        this.teamPlayer4 = playerIds.getLong(3);
        this.teamId4 = teamIds.getLong(3);
        this.teamScore4 = scores.getLong(3);
    }
}
