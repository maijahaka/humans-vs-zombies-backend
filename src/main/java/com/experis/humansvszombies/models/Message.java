package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import javax.persistence.*;
/*
* Entity class modelling a single chat message.
* A chat message can be either part of human or zombie chat.
*
* TODO: rename columns and refactor chat / message logic
 */
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "message")
    private String message;
    //TODO: rename column
    @Column(name="is_human_global")
    private boolean isHuman;
    //TODO: rename column
    @Column(name="is_zombie_global")
    private boolean isZombie;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @JsonGetter("chat")
    public String chat(){
        if (chat != null)
            return "/api/v1/games/" + chat.getId();
        return null;
    }

    @JsonGetter("player")
    public String player(){
        if (player != null)
            return "/api/v1/players/" + player.getId();
        return null;
    }


    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    public boolean isZombie() {
        return isZombie;
    }

    public void setZombie(boolean zombie) {
        isZombie = zombie;
    }
}
