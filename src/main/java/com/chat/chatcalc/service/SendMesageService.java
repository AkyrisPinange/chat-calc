package com.chat.chatcalc.service;

import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.model.SendMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SendMesageService {



    public ChatMessage sendMesage(SendMessage sendMesage){

        return new ChatMessage(
                UUID.randomUUID().toString(),
                sendMesage.getChat_id(),
                sendMesage.getContent(),
                "akyris",
                new Date().toString(),
                MessageType.JOIN);
    }
}
