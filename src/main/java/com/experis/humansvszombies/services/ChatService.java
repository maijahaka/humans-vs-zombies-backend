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

        //check if admin is doing the post,
        //admin can add only global messages
        if(defaultAuthenticationProvider.isAdmin()){
            Message newMessage = new Message();
            newMessage.setContent(message.getContent());
            newMessage.setHumanChat(false);
            newMessage.setPlayer(null);
            newMessage.setChat(chat);
            newMessage.setGlobalChat(true);
            newMessage.setSenderName("Admin");
            List<Message> msgList = chat.getMessages();
            msgList.add(message);
            messageRepository.save(newMessage);
            return newMessage;
        }

        String userId = defaultAuthenticationProvider.getPrincipal();
        Player player = playerRepository.findByUserIdAndGame_Id(userId, gameId);
        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        newMessage.setHumanChat(player.isHuman());
        newMessage.setGlobalChat(message.isGlobalChat());
        newMessage.setPlayer(player);
        newMessage.setChat(chat);
        newMessage.setSenderName(player.getPlayerName());
        List<Message> msgList = chat.getMessages();
        msgList.add(message);
        messageRepository.save(newMessage);
        return newMessage;
    }
    //returns global messages from the database
    public List<Message> getGlobalMessages(long id){
        if (!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");
        Game game = gameRepository.findById(id).get();
        long chatId = game.getChat().getId();
        return messageRepository.findAllByChat_IdAndGlobalChatIsTrue(chatId);
    }

    /*
    * If an admin is doing the get request, returns both zombie and human messages from the database
    * In case a human player does the get returns only human messages. Same goes for zombies.
     */
    public List<Message> getMessages(long id){
        if (!gameRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game ID not found");

        Game game = gameRepository.findById(id).get();
        long chatId = game.getChat().getId();
        //if JWT has admin role return all messages
        if(defaultAuthenticationProvider.isAdmin()) {
            System.out.println(defaultAuthenticationProvider.getAuthorities());
            return messageRepository.findAllByChat_Id(chatId);
        }

        String userId = defaultAuthenticationProvider.getPrincipal();
        if (playerRepository.findByUserIdAndGame_Id(userId, id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found in the game");

        Player player = playerRepository.findByUserIdAndGame_Id(userId, id);
        //check whether the player is a zombie or a human and return messages accordingly.
        if (player.isHuman())
            return messageRepository.findAllByChat_IdAndHumanChatIsTrueAndGlobalChatIsFalse(chatId);
        else
            return messageRepository.findAllByChat_IdAndHumanChatIsFalseAndGlobalChatIsFalse(chatId);
    }
}
