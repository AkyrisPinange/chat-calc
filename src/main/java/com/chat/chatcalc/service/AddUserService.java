package com.chat.chatcalc.service;


import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChatMessage;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
public class AddUserService {

    public ChatMessage addUser(SimpMessageHeaderAccessor headerAccessor,
                               ChatMessage chatMessage){
        String username = (String) chatMessage.getUsername();
        ChatMessage.builder()
                .type(MessageType.JOIN)
                .username(username)
                .build();
        return  chatMessage;
    }
}
