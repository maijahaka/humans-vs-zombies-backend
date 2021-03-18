package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping()
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        return gameService.addGame(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
        return gameService.deleteGame(id);
    }
}
