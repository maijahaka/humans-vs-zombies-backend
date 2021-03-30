package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUserIdAndGame_Id(String userId, long gameId); //find a user by userid (jwt subject_id) and game id
    Player findByIdAndGame_Id(long id, long gameId);    //find a user by id (user primary key) and game id
    Player findByBiteCode(String biteCode);
    List<Player> findAllByGame_Id(long gameId);     //find all players in a game by gameid

}