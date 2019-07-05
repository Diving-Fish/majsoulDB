package com.diving_fish.majsouldb.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ready")
public class Ready {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long teamId;

    @Column
    private Long round;

    @Column
    private Long id1;

    @Column
    private Long id2;

    @Column
    private Long id3;

    @Column
    private Long id4;

    @Column
    private Long id5;

    public Ready(Long team_id, Long round, Long id1, Long id2, Long id3, Long id4, Long id5) {
        this.teamId = team_id;
        this.round = round;
        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
        this.id4 = id4;
        this.id5 = id5;
    }

    public Long[] get() {
        return new Long[]{teamId, round, id1, id2, id3, id4, id5};
    }
}
