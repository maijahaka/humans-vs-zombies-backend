package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByGame_Id(Long gameId);
    Player findPlayerByBiteCode(String biteCode);
}