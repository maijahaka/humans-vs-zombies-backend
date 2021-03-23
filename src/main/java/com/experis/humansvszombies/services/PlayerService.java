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

    public List<Player> getAllPlayers(Long gameId) {
        return playerRepository.findAllByGame_Id(gameId);
    }

    public Player getPlayerById(Long gameId, Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            return null;
        }

        Player player = playerRepository.findById(playerId).get();

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

        Game game = gameRepository.getOne(gameId);
        player.setGame(game);

        player.setMessages(new ArrayList<>());
        player.setKills(new ArrayList<>());

        return playerRepository.save(player);
    }

    public Player updatePlayer(Long gameId, Long playerId, Player player) {
        if (!playerRepository.existsById(playerId)) {
            return null;
        }

        if (player.getGame().getId() != gameId) {
            return null;
        }

        return playerRepository.save(player);
    }

    public boolean deletePlayer(Long gameId, Long playerId) {
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
