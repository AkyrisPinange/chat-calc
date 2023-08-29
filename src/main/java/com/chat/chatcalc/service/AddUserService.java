package com.chat.chatcalc.service;


import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChatMessage;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
public class AddUserService {

    public ChatMessage addUser(SimpMessageHeaderAccessor headerAccessor,
                               ChatMessage chatMessage){
        String username = (String) chatMessage.getSender();
        ChatMessage.builder()
                .type(MessageType.JOIN)
                .sender(username)
                .build();
        return  chatMessage;
    }
}
