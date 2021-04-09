package com.experis.humansvszombies.services;

import com.experis.humansvszombies.models.*;
import com.experis.humansvszombies.models.wrappers.KillStatisticsWrapper;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.experis.humansvszombies.models.GameState.COMPLETE;

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
        List<Game> games = gameRepository.findAllByOrderById();
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
        Chat chat = new Chat();
        game.setChat(chat);
        return gameRepository.save(game);
    }

    /*
    * Updates a game object in the database and returns the modified object.
    *
    * Throws ResponseStatusException HttpStatus.BAD_REQUEST if request path ID != body id
    * Throws ResponseStatusException HttpStatus.BAD_REQUEST if no name is given in the request body
    * Throws ResponseStatusException NOT_FOUND if game doesn't exist.
    */
    public Game updateGame(long id, Game updatedGame) {

        if(id != updatedGame.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Path id doesn't match the request body id");
        if(!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        if (updatedGame.getName() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must name the game");

        Game game = gameRepository.getOne(id);

        if (updatedGame.getName() != null)
            game.setName(updatedGame.getName());
        if (updatedGame.getDescription() != null)
            game.setDescription(updatedGame.getDescription());
        if (updatedGame.getRules() != null)
            game.setRules(updatedGame.getRules());
        if (updatedGame.getGameState() != null)
            game.setGameState(updatedGame.getGameState());

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

    public Map<String, Object> getStatistics(long id) {
        if(!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        //get the game object and initialize map for response. If game is not over yet, throw http forbidden
        Game game = gameRepository.findById(id).get();
        if (game.getGameState() != COMPLETE)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Game is not over yet");
        Map<String, Object> statistics = new HashMap<String, Object>();

        //for each kill in the game construct a wrapper object, containing killer name, victim name and a timestamp
        List<Kill> gameKillList = game.getKills();
        ArrayList responseKillList = new ArrayList();
        for (Kill kill: gameKillList){
            KillStatisticsWrapper ksWrapper = new KillStatisticsWrapper();
            ksWrapper.setKillerName(kill.getKiller().getPlayerName());
            ksWrapper.setVictimName(kill.getVictim().getPlayerName());
            ksWrapper.setTimeStamp(kill.getTimeStamp());
            responseKillList.add(ksWrapper);
        }
        //number of kills in the game
        statistics.put("numKills", gameKillList.size());
        statistics.put("patientZero", gameRepository.patientZero(id));
        statistics.put("numHumans",  gameRepository.countHumans(id));
        statistics.put("numZombies", gameRepository.countZombies(id));
        statistics.put("topPlayer", gameRepository.topPlayer(id));
        statistics.put("killList", responseKillList);
        return statistics;
    }
}
