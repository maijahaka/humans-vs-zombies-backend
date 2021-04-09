package com.experis.humansvszombies.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "chat")
    private Game game;

    @OneToMany(cascade = CascadeType.ALL, mappedBy= "chat")
    private List<Message> messages;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @JsonGetter("messages")
    public List<String> messagesGetter() {
        return messages.stream()
                .map(message -> "/placeholder/" + message.getId())
                .collect(Collectors.toList());
    }

    @JsonGetter("game")
    public String game(){
        if (game != null)
            return "api/v1/games/" + game.getId();
        return null;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
