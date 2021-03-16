package com.experis.humansvszombies.models;

import javax.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_human")
    private boolean isHuman;

    @Column(name = "is_patient_zero")
    private boolean isPatientZero;

    @Column(name = "bite_code", unique = true)
    private String biteCode;

    public Player() {
        // players are humans unless otherwise specified
        this(false);
    }

    public Player(boolean isPatientZero) {
        this.isPatientZero = isPatientZero;

        // all players except "patient ones" are humans at first
        if (isPatientZero) {
            this.isHuman = false;
        } else {
            this.isHuman = true;
            this.biteCode = createBiteCode();
        }
    }

    private String createBiteCode() {
        // TODO confirm uniqueness
        return RandomStringUtils.randomNumeric(8);
    }
}
