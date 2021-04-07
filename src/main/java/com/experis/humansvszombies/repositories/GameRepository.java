package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

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

    @Query(
            value = "SELECT count(*) FROM game, player WHERE player.game_id = game.id AND game.id = ?1 AND is_human = true",
            nativeQuery = true
    )
    int countHumans(Long gameId);

    @Query(
            value = "SELECT count(*) FROM game, player WHERE player.game_id = game.id AND game.id = ?1 AND is_human = false",
            nativeQuery = true
    )
    int countZombies(Long gameId);

    @Query(
            value = "SELECT player_name FROM game, player WHERE player.game_id = game.id AND game.id = ?1 AND is_patient_zero = true",
            nativeQuery = true
    )
    String patientZero(Long gameId);

    @Query(
            value = "SELECT player_name as playerName, count(kill.killer_id) as kills\n" +
                    "FROM game, player, kill\n" +
                    "WHERE player.game_id = game.id AND game.id = ?1 AND kill.game_id = game.id AND kill.killer_id = player.id\n" +
                    "GROUP BY (player_name) ORDER BY kills DESC LIMIT 1\n",
            nativeQuery = true
    )
    Map<Player, Integer> topPlayer(Long gameId);
}
