package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.models.stomp.StompMessage;
import com.experis.humansvszombies.models.stomp.StompMessageType;
import com.experis.humansvszombies.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(@PathVariable long gameId) {
        List<Player> players = playerService.getAllPlayers(gameId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(players, status);
    }

    @GetMapping("/currentplayer")
    public ResponseEntity<Player> getLoggedPlayer(@PathVariable long gameId){
        return new ResponseEntity<>(playerService.getLoggedInPlayer(gameId), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getPlayerById(@PathVariable Long gameId, @PathVariable long userId){
        Object returnedPlayer = playerService.getPlayerById(gameId, userId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(returnedPlayer, status);
    }

    @PostMapping
    public  ResponseEntity<Player> addPlayer(@PathVariable Long gameId, @RequestBody Player player) {
        Player addedPlayer = playerService.addPlayer(gameId, player);
        HttpStatus status = HttpStatus.CREATED;
        StompMessage stompMessage = new StompMessage(gameId, StompMessageType.ADD_PLAYER);
        messagingTemplate.convertAndSend("/topic/addPlayer", stompMessage);
        return new ResponseEntity<>(addedPlayer, status);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long gameId, @PathVariable Long playerId, @RequestBody Player player) {
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

        StompMessage stompMessage = new StompMessage(gameId, StompMessageType.UPDATE_PLAYER);
        messagingTemplate.convertAndSend("/topic/updatePlayer", stompMessage);
        return new ResponseEntity<>(updatedPlayer, status);
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long gameId, @PathVariable Long playerId) {
        HttpStatus status;
        boolean wasDeleted = playerService.deletePlayer(gameId, playerId);

        if (wasDeleted) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        StompMessage stompMessage = new StompMessage(gameId, StompMessageType.DELETE_PLAYER);
        messagingTemplate.convertAndSend("/topic/deletePlayer", stompMessage);

        return new ResponseEntity<>(status);
    }
}
