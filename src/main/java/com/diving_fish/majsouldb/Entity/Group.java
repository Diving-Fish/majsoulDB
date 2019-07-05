package com.diving_fish.majsouldb.Entity;

import javax.persistence.*;

@Entity
@Table(name = "groupes")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer round;

    @Column
    private Long t1;

    @Column
    private Long t2;

    @Column
    private Long t3;

    @Column
    private Long t4;

    public Group(Integer round, Long t1, Long t2, Long t3, Long t4) {
        this.round = round;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
    }
}
