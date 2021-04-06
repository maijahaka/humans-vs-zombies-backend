package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByOrderById();
    boolean existsByName (String name);

    @Query(
            value = "SELECT g.id, g.name, g.game_state, g.description, g.rules, g.latitude, g.longitude, g.chat_id " +
                    "FROM game g " +
                    "LEFT JOIN player p ON g.id = p.game_id " +
                    "WHERE p.user_id = ?1",
            nativeQuery = true
    )
    List<Game> findAllByUserId (String userId);
}
