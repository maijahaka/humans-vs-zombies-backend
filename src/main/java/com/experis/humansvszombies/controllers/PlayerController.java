package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/games/{gameId}/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(@PathVariable Long gameId) {
        List<Player> players = playerService.getAllPlayers();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(players, status);
    }

    @GetMapping("/currentplayer")
    public ResponseEntity<Player> getLoggedPlayer(@PathVariable long gameId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String playerId = auth.getPrincipal().toString();
        System.out.println(playerId);
        return new ResponseEntity<>(playerService.getLoggedInPlayer(playerId, gameId), HttpStatus.OK);

    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long gameId, @PathVariable String playerId){
        Player returnedPlayer = playerService.getPlayerById(gameId, playerId);
        HttpStatus status;

        if (returnedPlayer != null) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnedPlayer, status);
    }

    @PostMapping
    public  ResponseEntity<Player> addPlayer(@PathVariable Long gameId, @RequestBody Player player) {
        HttpStatus status = HttpStatus.CREATED;
        Player addedPlayer = playerService.addPlayer(gameId, player);
        return new ResponseEntity<>(addedPlayer, status);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long gameId, @PathVariable String playerId, @RequestBody Player player) {
        HttpStatus status;

        if (!playerId.equals(player.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }

        Player updatedPlayer = playerService.updatePlayer(gameId, playerId, player);

        if (updatedPlayer == null) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        status = HttpStatus.OK;
        return new ResponseEntity<>(updatedPlayer, status);
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long gameId, @PathVariable String playerId) {
        HttpStatus status;
        boolean wasDeleted = playerService.deletePlayer(gameId, playerId);

        if (wasDeleted) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
