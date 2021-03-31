package com.experis.humansvszombies.repositories;

import com.experis.humansvszombies.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChat_Id(long id);
    List<Message> findAllByChat_IdAndHumanChatIsTrueAndGlobalChatIsFalse(long id);
    List<Message> findAllByChat_IdAndGlobalChatIsTrue(long id);
    List<Message> findAllByChat_IdAndHumanChatIsFalseAndGlobalChatIsFalse(long id);

}
