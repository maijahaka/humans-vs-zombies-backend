package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
    Player findByUserIdAndGame_Id(String userId, long gameId);
    Player findByIdAndGame_Id(long id, long gameId);
}