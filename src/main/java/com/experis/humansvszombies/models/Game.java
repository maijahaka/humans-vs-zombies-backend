package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/*
* Entity class modelling a single HVZ game.
*
* HVZ game is made of players that are either zombies or humans.
* Game has 3 states - registration, in progress and finished state. Players can only join the game in the registration state.
*
* HVZ Game has also functionality e.g. for a chat, for custom rules
* and for a custom location (displayed as a map in the front end).
 */

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_state")
    private GameState gameState;

    @Column(name = "description")
    private String description;

    @Column(name = "rules")
    private  String rules;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name= "longitude")
    private Float longitude;

    //one game can contain multiple players
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    List<Player> players;

    //one game can contain multiple kills
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    List<Kill> kills;

    //one game has one chat
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    //JsonGetters to stop infinite looping of json response
    @JsonGetter("players")
    public List<String> playersGetter() {
        return players.stream()
                .map(player -> "/api/v1/games/" + this.id + "/players/" + player.getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("kills")
    public List<String> killsGetter() {
        return kills.stream()
                .map(kill -> "/api/v1/games/" + this.id + "/kills/" + kill.getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("chat")
    public String chat(){
        if (chat != null)
            return "/api/v1/games/" + this.id + "/chat/";
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Kill> getKills() {
        return kills;
    }

    public void setKills(List<Kill> kills) {
        this.kills = kills;
    }
}
