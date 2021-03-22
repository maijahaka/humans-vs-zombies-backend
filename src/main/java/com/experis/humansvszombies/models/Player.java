package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "victim")
    private List<Kill> victims;

    @OneToOne(mappedBy = "killer")
    private Kill killer;

    @JsonGetter("messages")
    public List<String> messagesGetter() {
        return messages.stream()
                .map(message -> "/placeholder/" + message.getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("victims")
    public List<String> victimsGetter() {
        return victims.stream()
                .map(victim -> "/api/v1/players/" + victim.getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("game")
    public String game(){
        if (game != null)
            return "/api/v1/games/" + game.getId();
        return null;
    }

    @JsonGetter("killer")
    public String killer(){
        if (killer != null)
            return "/api/v1/users/" + killer.getId();
        return null;
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

    public List<Kill> getVictims() {
        return victims;
    }

    public void setVictims(List<Kill> victims) {
        this.victims = victims;
    }

    public Kill getKiller() {
        return killer;
    }

    public void setKiller(Kill killer) {
        this.killer = killer;
    }
}
