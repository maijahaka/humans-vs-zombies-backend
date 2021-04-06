package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChat_IdAndGlobalChatIsFalseOrderByTimeStamp(long id);
    List<Message> findAllByChat_IdAndHumanChatIsTrueAndGlobalChatIsFalseOrderByTimeStamp(long id);
    List<Message> findAllByChat_IdAndGlobalChatIsTrueOrderByTimeStamp(long id);
    List<Message> findAllByChat_IdAndHumanChatIsFalseAndGlobalChatIsFalseOrderByTimeStamp(long id);

}
