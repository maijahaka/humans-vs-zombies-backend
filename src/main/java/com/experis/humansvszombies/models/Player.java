package com.experis.humansvszombies.models;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "player")
    private List<Message> messages;

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

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
