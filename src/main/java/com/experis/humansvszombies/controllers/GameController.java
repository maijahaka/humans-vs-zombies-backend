package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @PostMapping()
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game addedGame = gameService.addGame(game);
        messagingTemplate.convertAndSend("/topic/addGame", addedGame.getId());
        return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable long id, @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(id, game);
        messagingTemplate.convertAndSend("/topic/updateGame", updatedGame.getId());
        return new ResponseEntity<>(updatedGame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable long id) {
        Game deleted  = gameService.deleteGame(id);
        messagingTemplate.convertAndSend("/topic/deleteGame", deleted.getId());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
