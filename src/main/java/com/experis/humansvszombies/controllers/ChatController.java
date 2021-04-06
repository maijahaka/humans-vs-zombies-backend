package com.experis.humansvszombies.controllers;

import com.experis.humansvszombies.models.Chat;
import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Message;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.MessageRepository;
import com.experis.humansvszombies.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/games/{id}/chat")
public class ChatController {


    @Autowired
    ChatService chatService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message, @PathVariable long id) {
        messagingTemplate.convertAndSend("/topic/addChatMessage", id);
        return new ResponseEntity<>(chatService.addMessage(message, id), HttpStatus.OK);
    }
    @GetMapping("/global")
    public ResponseEntity<List<Message>> getGlobalMessages(@PathVariable long id) {
        List<Message> messageList = chatService.getGlobalMessages(id);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Message>> getMessages(@PathVariable long id) {
        List<Message> messageList = chatService.getMessages(id);
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
}