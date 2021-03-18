package com.experis.humansvszombies.models;

import javax.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "chat_id")
    private List<Message> messages;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    public boolean isPatientZero() {
        return isPatientZero;
    }

    public void setPatientZero(boolean patientZero) {
        isPatientZero = patientZero;
    }

    public String getBiteCode() {
        return biteCode;
    }

    public void setBiteCode(String biteCode) {
        this.biteCode = biteCode;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
