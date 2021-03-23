package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.GameState;
import com.experis.humansvszombies.models.Message;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.MessageRepository;
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
    @Autowired
    private MessageRepository messageRepository;


    //Returns all games from the database
    public List<Game> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return games;
    }


    public Game getGameById(long id) {
        //If id doesn't exists return null
        if(!gameRepository.existsById(id)) {
            return null;
        }
        //If id exists return the game
        Game game = gameRepository.findById(id).get();
        return game;
    }

    //Sets starting game state for the game and checks if a name of the game is valid
    public Game addGame(Game game) {
        game.setGameState(GameState.REGISTRATION);
        if(game.getName() == null) {
            return null;
        }
        return gameRepository.save(game); //Adds the new game to the database
    }

    //Updates the game data to the database if path id matches to the game id in request body
    public Game updateGame(long id, Game game) {
        if(id != game.getId())
            return null;
        return gameRepository.save(game);
    }

    //Deletes the game by id if the id exists in the database
    public boolean deleteGame(long id) {
        if(gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }else
            return false;
    }

    public List<Message> getMessages(long id){
            if (gameRepository.existsById(id)) {
                Game game = gameRepository.getOne(id);
                long chatId = game.getChat().getId();
                //if player is zombie -> messageRepository.findAllByisZombieIsTrueAndChat_Id(chatId)
                //if human -> messageRepository.findAllByisHumanIsTrueAndChat_Id(chatId)
                return messageRepository.findAllByChat_Id(chatId);
            }else
                return null;
    }
}
