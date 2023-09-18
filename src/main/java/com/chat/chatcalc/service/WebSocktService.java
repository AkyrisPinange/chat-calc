package com.chat.chatcalc.service;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.entiteis.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class WebSocktService {
    private final SimpMessagingTemplate messagingTemplate;
    private static final String PATH = "/topic/chat/";

    public WebSocktService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void createChatMessage(Chats chat, User user, String content) {
        sendMessage(PATH + user.getId(), user, chat, content, MessageType.CREATE);
    }

    public void sendChatMessage(Chats chat, User user, String content) {
        sendMessage(PATH + chat.getId(), user, chat, content, MessageType.SEND);
    }

    public void joinChatMessage(Chats chat, User user, String content) {
        sendMessage(PATH + chat.getId(), user, chat, content, MessageType.JOIN);
    }

    public void errorMessageByChatId(String chatId, String message) {
        messagingTemplate.convertAndSend(PATH + chatId, new ChatMessage(message, MessageType.ERROR));
    }
    public void errorMessageByUser(String idUser, String message) {
        messagingTemplate.convertAndSend(PATH + idUser, new ChatMessage(message, MessageType.ERROR));
    }

    private void sendMessage(String path, User user, Chats chat, String content, MessageType messageType) {
        messagingTemplate.convertAndSend(path,
                new ChatMessage(
                        UUID.randomUUID().toString(),
                        chat.getId(),
                        content,
                        user.getUsername(),
                        new Date().toString(),
                        messageType));
    }
}
