package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.GameState;
import com.experis.humansvszombies.models.Message;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
/*
* A service class for game controller. Separates the business logic from the controller logic.
* Throws ResponseStatusException if request to game controller endpoint is not valid.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MessageRepository messageRepository;


    /*
    * Returns all games as a list from the database.
    */
    public List<Game> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return games;
    }

    /*
    * Returns a single game from the database by game id.
    *
    * Throws ResponseStatusException NOT_FOUND if game doesn't exist.
    */
    public Game getGameById(long id) {
        if(!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        return gameRepository.findById(id).get();
    }

    /*
    * Adds new game to the database. Adds the game state as REGISTRATION and constructs
    * empty lists for players and kills in the game.
    *
    * Returns the added new game object.
    * Throws ResponseStatusException HttpStatus.BAD_REQUEST if no name for the game is supplied or
    * if the name is already in use.
    */
    public Game addGame(Game game) {
        if(game.getName() == null || gameRepository.existsByName(game.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No name given or a game with given name already exists");

        game.setGameState(GameState.REGISTRATION);
        game.setPlayers(new ArrayList<>());
        game.setKills(new ArrayList<>());
        return gameRepository.save(game);
    }

    /*
    * Updates a game object in the database and returns the modified object.
    *
    * Throws ResponseStatusException HttpStatus.BAD_REQUEST if request path ID != body id
    * Throws ResponseStatusException HttpStatus.BAD_REQUEST if no name is given in the request body
    * Throws ResponseStatusException NOT_FOUND if game doesn't exist.
    */
    public Game updateGame(long id, Game game) {
        if(id != game.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path id doesn't match the request body id");
        if(!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        if (game.getName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must name the game");

        return gameRepository.save(game);
    }

    /*
     * Deletes a game object in the database and returns the deleted object.
     *
     * Throws ResponseStatusException NOT_FOUND if game doesn't exist.
     */
    public Game deleteGame(long id) {
        if(!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");

         Game deleted = gameRepository.findById(id).get();
         gameRepository.deleteById(id);
         return deleted;
    }

    /*
     * Returns the chat messages of the game as message list.
     *
     * Throws ResponseStatusException NOT_FOUND if game doesn't exist.
     * TODO: Move to chat service
     */
    public List<Message> getMessages(long id){
        if (!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        Game game = gameRepository.getOne(id);
        long chatId = game.getChat().getId();
        //if player is zombie -> messageRepository.findAllByisZombieIsTrueAndChat_Id(chatId)
        //if human -> messageRepository.findAllByisHumanIsTrueAndChat_Id(chatId)
        return messageRepository.findAllByChat_Id(chatId);
    }
}
