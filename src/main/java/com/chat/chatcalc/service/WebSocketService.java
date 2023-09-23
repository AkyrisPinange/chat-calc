package com.chat.chatcalc.service;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.entiteis.ChatMessage;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import java.util.Date;
import java.util.UUID;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatsRepository chatsRepository;
    @Value("${websocket.uri.prefix}")
    private String path;

    public WebSocketService(SimpMessagingTemplate messagingTemplate, ChatsRepository chatsRepository) {
        this.messagingTemplate = messagingTemplate;
        this.chatsRepository = chatsRepository;
    }

    public void createChatMessage(Chats chat, User user, String content) {
        sendMessage(path + user.getId(), user, chat, content, MessageType.CREATE);
    }

    public void sendChatMessage(Chats chat, User user, String content) {
        sendMessage(path + chat.getId(), user, chat, content, MessageType.SEND);
    }

    public void joinChatMessage(Chats chat, User user, String content) {
        sendMessage(path + chat.getId(), user, chat, content, MessageType.JOIN);
    }

    public void errorMessageByChatId(String chatId, String message) {
        messagingTemplate.convertAndSend(path + chatId, new ChatMessage(message, MessageType.ERROR));
    }

    public void errorMessageByUser(String idUser, String message) {
        messagingTemplate.convertAndSend(path + idUser, new ChatMessage(message, MessageType.ERROR));
    }

    private void sendMessage(String path, User user, Chats chat, String content, MessageType messageType) {
        ChatMessage message = new ChatMessage(
                UUID.randomUUID().toString(),
                chat.getId(),
                user.getId(),
                content,
                user.getUsername(),
                new Date().toString(),
                messageType);

        chat.getMessages().add(message);
        chatsRepository.save(chat);

        messagingTemplate.convertAndSend(path, message);
    }
}
