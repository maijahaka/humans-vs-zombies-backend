package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUserIdAndGame_Id(String userId, long gameId); //find a user by userid (jwt subject_id) and game id
    <T> T findByIdAndGame_Id (long id, long gameId, Class<T> type);
    Player findByIdAndGame_Id (long id, long gameId);
    Player findByBiteCode(String biteCode);
    <T> List<T> findAllByGame_IdOrderById (long gameId, Class<T> type);
    List<Player> findAllByGame_IdOrderById(long gameId);     //find all players in a game by gameid
    Player findPlayerByBiteCode(String biteCode);
    boolean existsByBiteCode(String biteCode);

}