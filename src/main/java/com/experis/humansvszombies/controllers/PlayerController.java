package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(players, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player returnedPlayer = playerService.getPlayerById(id);
        HttpStatus status;

        if (returnedPlayer != null) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(returnedPlayer, status);
    }

    @PostMapping
    public  ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        HttpStatus status = HttpStatus.CREATED;
        Player addedPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(addedPlayer, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        HttpStatus status;

        if (!id.equals(player.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }

        Player updatedPlayer = playerService.updatePlayer(id, player);

        if (updatedPlayer == null) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        status = HttpStatus.OK;
        return new ResponseEntity<>(updatedPlayer, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        HttpStatus status;
        boolean wasDeleted = playerService.deletePlayer(id);

        if (wasDeleted) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
