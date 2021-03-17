package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/games")
@RestController
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping()
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(games, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        HttpStatus status;
        if(!gameRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(null, status);
        }
        status = HttpStatus.OK;
        Game game = gameRepository.findById(id).get();
        return new ResponseEntity<>(game, status);
    }

    @PostMapping()
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game returnGame = gameRepository.save(game);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnGame, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
        HttpStatus status;
        if(!id.equals(game.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(null, status);
        }
        Game returnGame = gameRepository.save(game);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnGame, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
        HttpStatus status;
        if(gameRepository.existsById(id)) {
            status = HttpStatus.OK;
            gameRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }
}
