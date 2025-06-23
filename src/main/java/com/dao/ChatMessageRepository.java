package com.dao;

import com.model.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.senderUsername = :sender AND m.receiverUsername = :receiver) " +
           "OR (m.senderUsername = :receiver AND m.receiverUsername = :sender) " +
           "ORDER BY m.timestamp ASC")
    List<ChatMessage> findByUsers(String sender, String receiver);
}
