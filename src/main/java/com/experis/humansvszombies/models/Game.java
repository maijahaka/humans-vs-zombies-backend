package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "game")
    List<Player> players;

    @OneToMany(mappedBy = "game")
    List<Kill> kills;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @JsonGetter("players")
    public List<String> playersGetter() {
        return players.stream()
                .map(player -> "/api/v1/players/" + player.getId())
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
