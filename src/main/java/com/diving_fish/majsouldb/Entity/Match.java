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

    public Match(Integer round, Integer position, Long teamPlayer1, Long teamId1, Long teamScore1, Long teamPlayer2, Long teamId2, Long teamScore2, Long teamPlayer3, Long teamId3, Long teamScore3, Long teamPlayer4, Long teamId4, Long teamScore4) {
        this.round = round;
        this.position = position;
        this.teamPlayer1 = teamPlayer1;
        this.teamId1 = teamId1;
        this.teamScore1 = teamScore1;
        this.teamPlayer2 = teamPlayer2;
        this.teamId2 = teamId2;
        this.teamScore2 = teamScore2;
        this.teamPlayer3 = teamPlayer3;
        this.teamId3 = teamId3;
        this.teamScore3 = teamScore3;
        this.teamPlayer4 = teamPlayer4;
        this.teamId4 = teamId4;
        this.teamScore4 = teamScore4;
    }
}
