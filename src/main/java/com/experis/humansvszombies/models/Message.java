package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Column(name = "content")
    private String content;
    @Column(name="humanChat")
    private boolean humanChat;
    @Column(name ="globalChat")
    private boolean globalChat;
    @Column(name = "sender_name")
    private String senderName;

    @CreationTimestamp
    @Column(name ="timestamp")
    private LocalDateTime timeStamp;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHumanChat() {
        return humanChat;
    }

    public void setHumanChat(boolean humanChat) {
        this.humanChat = humanChat;
    }

    public boolean isGlobalChat() {
        return globalChat;
    }

    public void setGlobalChat(boolean globalChat) {
        this.globalChat = globalChat;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
