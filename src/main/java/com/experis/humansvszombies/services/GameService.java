package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.GameState;
import com.experis.humansvszombies.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    //Returns all games from the database
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    //Checks if the game id is in the database and returns a response depending on that
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        //If id doesn't exists, returns null and Http status 404
        if(!gameRepository.existsById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        //If id exists, returns game by id and Http status 200
        Game game = gameRepository.findById(id).get();
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    //Sets starting game state for the game and checks if a name of the game is valid
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        game.setGameState(GameState.REGISTRATION);
        if(game.getName() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Game returnGame = gameRepository.save(game); //Adds the new game to the database
        return new ResponseEntity<>(returnGame, HttpStatus.CREATED);
    }

    //Updates the game data to the database if path id matches to the game id in request body
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
        if(!id.equals(game.getId())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Game returnGame = gameRepository.save(game);
        return new ResponseEntity<>(returnGame, HttpStatus.OK);
    }

    //Deletes the game by id if the id exists in the database
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
