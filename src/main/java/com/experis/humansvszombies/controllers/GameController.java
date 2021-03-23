package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Message;
import com.experis.humansvszombies.models.wrappers.ResponseWrapper;
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
    public ResponseEntity<ResponseWrapper> getAllGames() {
        List<Game> gameList = gameService.getAllGames();
        ResponseWrapper responseWrapper = new ResponseWrapper(gameList, HttpStatus.OK);
        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getGameById(@PathVariable long id) {
        Game game = gameService.getGameById(id);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        HttpStatus status;
        if (game != null) {
            responseWrapper.setData(game);
            status = HttpStatus.OK;
        }
        else{
            responseWrapper.setData(null);
            status = HttpStatus.NOT_FOUND;
        }
        responseWrapper.setHttpStatus(status);
        return new ResponseEntity<>(responseWrapper, status);
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper> addGame(@RequestBody Game game) {
        Game addedGame = gameService.addGame(game);
        ResponseWrapper responseWrapper = new ResponseWrapper();
        HttpStatus status;
        if (addedGame != null) {
            responseWrapper.setData(game);
            status = HttpStatus.OK;
        }
        else
            status = HttpStatus.BAD_REQUEST;
        responseWrapper.setHttpStatus(status);
        return new ResponseEntity<>(responseWrapper, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateGame(@PathVariable long id, @RequestBody Game game) {
        Game updatedGame = gameService.updateGame(id, game);
        HttpStatus status;
        ResponseWrapper responseWrapper = new ResponseWrapper();
        if (updatedGame != null){
            responseWrapper.setData(updatedGame);
            status = HttpStatus.OK;
        }else
            status = HttpStatus.BAD_REQUEST;
        responseWrapper.setHttpStatus(status);
        return new ResponseEntity<>(responseWrapper, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteGame(@PathVariable long id) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        HttpStatus status;
        boolean deleted  = gameService.deleteGame(id);
        if (deleted){
            responseWrapper.setData(true);
            status = HttpStatus.OK;
        }else{
            responseWrapper.setData(false);
            status = HttpStatus.NOT_FOUND;
        }
        responseWrapper.setHttpStatus(status);
        return new ResponseEntity<>(responseWrapper, status);
    }

    @GetMapping("/{id}/chat")
    public ResponseEntity<List<Message>> getMessages(@PathVariable long id) {
        List<Message> messageList = gameService.getMessages(id);
        ResponseWrapper responseWrapper = new ResponseWrapper(messageList, HttpStatus.OK);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
}
