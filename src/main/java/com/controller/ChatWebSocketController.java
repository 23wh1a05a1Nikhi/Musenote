package com.controller;

import com.model.ChatMessageDTO;
import com.service.ChatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatWebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private ChatService chatService;

    @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload ChatMessageDTO message) {
        chatService.saveMessage(message);  // 💾 Save to DB
        messagingTemplate.convertAndSendToUser(
            message.getReceiverUsername(),
            "/private",
            message
        );
    }
    @GetMapping("/api/chat/history/{sender}/{receiver}")
    public List<ChatMessageDTO> getChatHistory(@PathVariable String sender, @PathVariable String receiver) {
        return chatService.getChatHistory(sender, receiver);
    }

}
