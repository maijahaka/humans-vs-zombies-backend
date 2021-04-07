package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
* Entity class modelling a kill in the game.
* Every kill has a single victim and a single killer.
* A player can have multiple kills.
 */

@Entity
public class Kill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "story")
    private String story;
    @Column(name="lat")
    private float lat;
    @Column(name="lng")
    private float lng;
    @CreationTimestamp
    @Column(name ="time_of_death")
    private LocalDateTime timeStamp;

    //victim of the kill
    @OneToOne
    @JoinColumn(name="victim_id")
    private Player victim;
    //a game can have multiple kills
    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;
    //a player can have multiple kills
    @ManyToOne
    @JoinColumn(name="killer_id")
    private Player killer;

    @JsonGetter("game")
    public String game(){
        if (game != null)
            return "/api/v1/games/" + game.getId();
        return null;
    }

    @JsonGetter
    public String victim(){
        if (this.victim != null)
            return "/api/v1/games/" + this.game.getId() + "/players/" + victim.getId();
        return null;
    }
    @JsonGetter
    public String killer(){
        if (this.killer != null)
            return "/api/v1/games/" + this.game.getId() +"/players/" + killer.getId();
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Player getVictim() {
        return victim;
    }

    public void setVictim(Player victim) {
        this.victim = victim;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getKiller() {
        return killer;
    }

    public void setKiller(Player killer) {
        this.killer = killer;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
