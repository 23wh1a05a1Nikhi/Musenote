package com.mapper;
import java.time.LocalDateTime;
import com.model.ChatMessage;
import com.model.ChatMessageDTO;

public class ChatMapper {

    public static ChatMessage toEntity(ChatMessageDTO dto) {
        ChatMessage message = new ChatMessage();
        message.setSenderUsername(dto.getSenderUsername());
        message.setReceiverUsername(dto.getReceiverUsername());
        message.setContent(dto.getContent());
        message.setTimestamp(LocalDateTime.now()); // or parse dto.getTimestamp()
        return message;
    }

    public static ChatMessageDTO toDTO(ChatMessage message) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setSenderUsername(message.getSenderUsername());
        dto.setReceiverUsername(message.getReceiverUsername());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp()); 
        return dto;
    }
}
