package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Kill;
import com.experis.humansvszombies.models.stomp.KillStompMessage;
import com.experis.humansvszombies.models.stomp.StompMessageType;
import com.experis.humansvszombies.models.wrappers.BiteCodeKillerWrapper;
import com.experis.humansvszombies.services.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/games/{gameId}/kills")
@RestController
@CrossOrigin(origins = "${ALLOWED_ORIGINS}")
public class KillController {
    @Autowired
    private KillService killService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

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
        KillStompMessage stompMessage = new KillStompMessage(gameId, StompMessageType.ADD_KILL, addedKill.getVictim().getId(), addedKill.getKiller().getPlayerName(), addedKill.getStory());
        messagingTemplate.convertAndSend("/topic/addKill", stompMessage);
        return new ResponseEntity<>(addedKill, status);
    }

    @DeleteMapping("/{killId}")
    public ResponseEntity<Kill> deleteKill(@PathVariable long killId){
        return new ResponseEntity<>(killService.deleteKill(killId), HttpStatus.OK);
    }

    @PutMapping("/{killId}")
    public ResponseEntity<Kill> updateKill(@PathVariable long killId, @RequestBody Kill kill){
        return new ResponseEntity<Kill>(killService.updateKill(killId, kill), HttpStatus.OK);
    }
}
