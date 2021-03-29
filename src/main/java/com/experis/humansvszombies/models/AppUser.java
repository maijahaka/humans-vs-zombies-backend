package com.experis.humansvszombies.models;
import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    //@OneToMany(mappedBy = "user")
    //private List<Player> players;

    public long getId() {
        return this.id;
    }

   // @JsonGetter("players")
    //public List<String> playersGetter() {
    //    return players.stream()
    //            .map(player -> "/placeholder/" +  player.getId())
     //           .collect(Collectors.toList());
        //.map(player -> "/games/" + player.getGame().getId() + player.getId())
    //}

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //public List<Player> getPlayers() {       return players;  }

   // public void setPlayers(List<Player> players) {  this.players = players;  }
}
