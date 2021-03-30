package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Kill;
import com.experis.humansvszombies.models.wrappers.BiteCodeKillerWrapper;
import com.experis.humansvszombies.services.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/games/{gameId}/kills")
@RestController
@CrossOrigin(origins = "*")
public class KillController {
    @Autowired
    private KillService killService;

    @GetMapping
    public ResponseEntity<List<Kill>> getAllKills(@PathVariable long gameId) {
        List<Kill> kills = killService.getAllKills(gameId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(kills, status);
    }
    @GetMapping("/{killId}")
    public ResponseEntity<Kill> getKill(@PathVariable long gameId, @PathVariable long killId) {
        Kill kill = killService.getKill(killId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(kill, status);
    }
   @PostMapping
    public ResponseEntity<Kill> addKill(@RequestBody BiteCodeKillerWrapper kill, @PathVariable long gameId){
        Kill addedKill = killService.addKill(kill, gameId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(addedKill, status);
    }
}
