package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.ChatMessage;
import com.model.ChatMessageDTO;
import com.dao.ChatMessageRepository;
import com.mapper.ChatMapper;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Override
    public void saveMessage(ChatMessageDTO messageDTO) {
        ChatMessage message = ChatMapper.toEntity(messageDTO);
        chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessageDTO> getChatHistory(String sender, String receiver) {
        List<ChatMessage> messages = chatMessageRepository.findByUsers(sender, receiver);
        return messages.stream()
                .map(ChatMapper::toDTO)
                .collect(Collectors.toList());
    }
}
