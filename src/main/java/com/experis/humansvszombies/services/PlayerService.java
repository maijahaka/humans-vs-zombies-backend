package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.PlayerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    public Player getLoggedInPlayer(String playerId, long gameId){
        return playerRepository.findByPlayerIdAndGame_Id(playerId, gameId);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long gameId, String playerId) {
        if (playerRepository.findByPlayerIdAndGame_Id(playerId, gameId) == null) {
            return null;
        }

        Player player = playerRepository.findByPlayerIdAndGame_Id(playerId, gameId);

        if (player.getGame().getId() != gameId) {
            return null;
        }

        return player;
    }

    public Player addPlayer(Long gameId, Player player) {
        if (!player.isPatientZero()) {
            player.setHuman(true);
        } else {
            player.setHuman(false);
        }
        player.setBiteCode(createBiteCode());

        Game game = gameRepository.findById(gameId).get();
        player.setGame(game);
        player.setMessages(new ArrayList<>());
        player.setKills(new ArrayList<>());

        return playerRepository.save(player);
    }

    public Player updatePlayer(Long gameId, String playerId, Player player) {
        if (!playerRepository.existsById(playerId)) {
            return null;
        }

        if (player.getGame().getId() != gameId) {
            return null;
        }

        return playerRepository.save(player);
    }

    public boolean deletePlayer(Long gameId, String playerId) {
        if (!playerRepository.existsById(playerId)) {
            return false;
        }

        Player player = playerRepository.findById(playerId).get();

        if (player.getGame().getId() != gameId) {
            return false;
        }

        playerRepository.deleteById(playerId);
        return true;
    }

    private String createBiteCode() {
        // TODO confirm uniqueness
        return RandomStringUtils.randomNumeric(6);
    }
}
