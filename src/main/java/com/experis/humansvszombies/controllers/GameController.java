package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.stomp.StompMessage;
import com.experis.humansvszombies.models.stomp.StompMessageType;
import com.experis.humansvszombies.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/games")
@RestController
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> gameList = gameService.getAllGames();
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable long id) {
        Game game = gameService.getGameById(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }
    @RolesAllowed("admin")
    @PostMapping()
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game addedGame = gameService.addGame(game);
        StompMessage stompMessage = new StompMessage(addedGame.getId(), StompMessageType.ADD_GAME);
        messagingTemplate.convertAndSend("/topic/addGame", stompMessage);
        return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
    }
    @RolesAllowed("admin")
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable long id, @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(id, game);
        StompMessage stompMessage = new StompMessage(updatedGame.getId(), StompMessageType.UPDATE_GAME);
        messagingTemplate.convertAndSend("/topic/updateGame", stompMessage);
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @RolesAllowed("admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable long id) {
        Game deleted  = gameService.deleteGame(id);
        StompMessage stompMessage = new StompMessage(deleted.getId(), StompMessageType.DELETE_GAME);
        messagingTemplate.convertAndSend("/topic/deleteGame", stompMessage);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(@PathVariable long id) {
        Map<String, Object> responseMap = gameService.getStatistics(id);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
