package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_human")
    private boolean isHuman;

    @Column(name = "is_patient_zero")
    private boolean isPatientZero;

    @Column(name = "bite_code", unique = true)
    private String biteCode;

   //@ManyToOne
    //@JoinColumn(name="user_id")
    //private AppUser user;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "player")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "killer")
    private List<Kill> kills;

    @OneToOne(mappedBy ="victim")
    private Kill victimOf;

    @JsonGetter("messages")
    public List<String> messagesGetter() {
        return messages.stream()
                .map(message -> "/placeholder/" + message.getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("kills")
    public List<String> victimsGetter() {
        return this.kills.stream()
                .map(kill -> "/api/v1/players/" + kill.getVictim().getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("victimOf")
    public String victimOf(){
        if (this.victimOf != null)
            return "/api/v1/players/" + this.victimOf.getKiller().getId();
        return null;
    }

    @JsonGetter("game")
    public String game(){
        if (game != null)
            return "/api/v1/games/" + game.getId();
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

    //public AppUser getUser() {   return user;   }

   // public void setUser(AppUser user) { this.user = user;  }

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

    public List<Kill> getKills() {
        return kills;
    }

    public void setKills(List<Kill> kills) {
        this.kills = kills;
    }

    public Kill getVictimOf() {
        return victimOf;
    }

    public void setVictimOf(Kill victimOf) {
        this.victimOf = victimOf;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
