package com.experis.humansvszombies.services;

import com.experis.humansvszombies.config.DefaultAuthenticationProvider;
import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.KillRepository;
import com.experis.humansvszombies.repositories.PlayerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
/*
* Player service acts as an middle-man between Player REST controller and Player Repository.
*
 */
@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    KillRepository killRepository;

    //provides information about the authentication token
    @Autowired
    DefaultAuthenticationProvider authentication;

    //returns the player object for the authenticated user in a given game
    public Player getLoggedInPlayer(long gameId){

        //if get was made by 'anonymousUser' throw http unauthorized
        if (authentication.getAuthentication() instanceof AnonymousAuthenticationToken)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in as a user");

        //if player hasn't registered in the game throw http not found
        String userId = authentication.getPrincipal();
        if (playerRepository.findByUserIdAndGame_Id(userId, gameId) == null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"User has no player object in the game");

        return playerRepository.findByUserIdAndGame_Id(userId, gameId);
    }

    //return all player objects in given game
    public List<Player> getAllPlayers(long gameId) {
        return playerRepository.findAllByGame_Id(gameId);
    }

    //return a player by primary key in given game, if one exists
    public Player getPlayerById(Long gameId, long id) {
        //if no player object is found with the given player primary key throw http not found
        if (playerRepository.findByIdAndGame_Id(id, gameId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No player id found in the game");
        }
        return playerRepository.findByIdAndGame_Id(id, gameId);
    }

    //adds / registers a player to the given game.
    public Player addPlayer(Long gameId, Player player) {
        //subject_id of the authenticated users JWT token
        String userId = authentication.getPrincipal();
        System.out.println(userId);
        //if player has already registered throw http bad request
        if (playerRepository.findByUserIdAndGame_Id(userId, gameId) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already registered in the game");
        if (!gameRepository.existsById(gameId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The game doesn't exist");

        //Set default values for isHuman and isPatientZero
        player.setHuman(true);
        player.setPatientZero(false);

       //create a unique bite code string for the player
        player.setBiteCode(createBiteCode());
        //set user_id as jwt subject_id
        player.setUserId(userId);
        Game game = gameRepository.findById(gameId).get();
        //set players game and initialize empty arrays for kills and messages
        player.setGame(game);
        player.setMessages(new ArrayList<>());
        player.setKills(new ArrayList<>());

        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(Long gameId, Long playerId, Player player) {
        if (!playerRepository.existsById(playerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No player id found in the game");
        }

        if (player.getGame().getId() != gameId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path variable and request body doesn't match");
        }

        // if a player that has been killed is turned back into a human, the kill is deleted
        if (player.isHuman() && killRepository.existsByVictimId(player.getId())) {
            killRepository.deleteByVictimId(player.getId());
        }

        return playerRepository.save(player);
    }

    //deletes a player from the game
    public boolean deletePlayer(Long gameId, Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No player id found in the game");
        }

        playerRepository.deleteById(playerId);
        return true;
    }

    private String createBiteCode() {
        // TODO confirm uniqueness
        return RandomStringUtils.randomNumeric(6);
    }
}
