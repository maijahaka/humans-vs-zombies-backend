package com.experis.humansvszombies.services;

import com.experis.humansvszombies.config.DefaultAuthenticationProvider;
import com.experis.humansvszombies.models.Chat;
import com.experis.humansvszombies.models.Game;
import com.experis.humansvszombies.models.Message;
import com.experis.humansvszombies.models.Player;
import com.experis.humansvszombies.repositories.GameRepository;
import com.experis.humansvszombies.repositories.MessageRepository;
import com.experis.humansvszombies.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    DefaultAuthenticationProvider defaultAuthenticationProvider;

    public Message addMessage(Message message, long gameId){
        Game game = gameRepository.findById(gameId).get();
        Chat chat = game.getChat();
        String userId = defaultAuthenticationProvider.getPrincipal();
        Player player = playerRepository.findByUserIdAndGame_Id(userId, gameId);
        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        newMessage.setHumanChat(player.isHuman());
        newMessage.setGlobalChat(message.isGlobalChat());
        newMessage.setPlayer(player);
        newMessage.setChat(chat);
        List<Message> msgList = chat.getMessages();
        msgList.add(message);
        messageRepository.save(newMessage);
        return newMessage;
    }
    public List<Message> getGlobalMessages(long id){
        if (!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        Game game = gameRepository.findById(id).get();
        long chatId = game.getChat().getId();
        //if player is zombie -> messageRepository.findAllByisZombieIsTrueAndChat_Id(chatId)
        //if human -> messageRepository.findAllByisHumanIsTrueAndChat_Id(chatId)
        return messageRepository.findAllByChat_IdAndGlobalChatIsTrue(chatId);
    }

    public List<Message> getMessages(long id){
        if (!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        Game game = gameRepository.findById(id).get();
        long chatId = game.getChat().getId();
        String userId = defaultAuthenticationProvider.getPrincipal();
        if (playerRepository.findByUserIdAndGame_Id(userId, id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found in the game");

        Player player = playerRepository.findByUserIdAndGame_Id(userId, id);
        if (player.isHuman())
            return messageRepository.findAllByChat_IdAndHumanChatIsTrue(chatId);
        else
            return messageRepository.findAllByChat_IdAndHumanChatIsFalseAndGlobalChatIsFalse(chatId);
    }
}
