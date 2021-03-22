package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


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
    //@Column(name ="time_of_death")
    //private Date date;

    @OneToOne
    @JoinColumn(name="killer_id")
    private Player killer;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name="victim_id")
    private Player victim;

    @JsonGetter("game")
    public String game(){
        if (game != null)
            return "/api/v1/games/" + game.getId();
        return null;
    }

    @JsonGetter("killer")
    public String killer(){
        if (killer != null)
            return "/api/v1/players/" + killer.getId();
        return null;
    }

    @JsonGetter("victim")
    public String victim(){
        if (victim != null)
            return "/api/v1/players/" + victim.getId();
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

    public Player getVictim() {
        return victim;
    }

    public void setVictim(Player victim) {
        this.victim = victim;
    }


    //public Date getDate() {
    //    return date;
    //}

    //public void setDate(Date date) {
    //    this.date = date;
    //}
}
