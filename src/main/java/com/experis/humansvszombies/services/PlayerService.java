package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.repositories.PlayerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        if (!playerRepository.existsById(id)) {
            return null;
        }

        return playerRepository.findById(id).get();
    }

    public Player addPlayer(Player player) {
        if (!player.isPatientZero()) {
            player.setHuman(true);
        } else {
            player.setHuman(false);
        }

        player.setBiteCode(createBiteCode());

        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player player) {
        if (!playerRepository.existsById(id)) {
            return null;
        }

        return playerRepository.save(player);
    }

    public boolean deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            return false;
        }

        playerRepository.deleteById(id);
        return true;
    }

    private String createBiteCode() {
        // TODO confirm uniqueness
        return RandomStringUtils.randomNumeric(6);
    }
}
