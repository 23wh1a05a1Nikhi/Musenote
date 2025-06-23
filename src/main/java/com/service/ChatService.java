package com.service;

import java.util.List;
import com.model.ChatMessageDTO;

public interface ChatService {
    void saveMessage(ChatMessageDTO messageDTO);
    List<ChatMessageDTO> getChatHistory(String sender, String receiver);
}
