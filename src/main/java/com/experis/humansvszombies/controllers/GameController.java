package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Message;
import com.experis.humansvszombies.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/games")
@RestController
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping()
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> gameList = gameService.getAllGames();
        return new ResponseEntity<>(gameList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable long id) {
        Game game = gameService.getGameById(id);
        if (game != null)
            return new ResponseEntity<>(game, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game addedGame = gameService.addGame(game);
        if (addedGame != null)
            return new ResponseEntity<>(addedGame, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable long id, @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(id, game);
        if (updatedGame != null)
            return new ResponseEntity<>(updatedGame, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGame(@PathVariable long id) {
        boolean deleted  = gameService.deleteGame(id);
        if (deleted)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/chat")
    public ResponseEntity<List<Message>> getMessages(@PathVariable long id) {
        List<Message> messageList = gameService.getMessages(id);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
}
