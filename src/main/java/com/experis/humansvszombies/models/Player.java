package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/*
* Entity class modeling a player in the game.

* A player is either a zombie or a human. Zombies can inflict (kill) human players.
* Player has an unique bite code that is given to the zombie when killed. Inputting this bite code will turn the
* player into a zombie.
*
* Each player object has a 'userId' column which refers to JWT token subject_id field of authenticated user.
 */

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //column for JWT token subject_id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "is_human")
    private boolean isHuman;

    @Column(name = "is_patient_zero")
    private boolean isPatientZero;

    @Column(name = "bite_code", unique = true)
    private String biteCode;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "killer")
    private List<Kill> kills;

    @OneToOne(mappedBy ="victim")
    private Kill victimOf;

    @JsonGetter("kills")
    public List<String> victimsGetter() {
        return this.kills.stream()
                .map(kill -> "/api/v1/games/" + this.getGame().getId() + "/players/" + kill.getVictim().getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("victimOf")
    public String victimOf(){
        if (this.victimOf != null)
            return "/api/v1/games/" +this.victimOf.getKiller().getGame().getId() + "/players/" + this.victimOf.getKiller().getId();
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
