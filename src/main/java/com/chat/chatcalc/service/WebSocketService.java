package com.chat.chatcalc.service;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.entiteis.ChatMessage;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.utils.Generate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import java.util.UUID;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatsRepository chatsRepository;
    private final Generate generate;
    @Value("${websocket.uri.prefix}")
    private String path;

    public WebSocketService(SimpMessagingTemplate messagingTemplate, ChatsRepository chatsRepository, Generate generate) {
        this.messagingTemplate = messagingTemplate;
        this.chatsRepository = chatsRepository;
        this.generate = generate;
    }


    public void errorMessageByChatId(String chatId, String message) {
        messagingTemplate.convertAndSend(getChatPath(chatId), new ChatMessage(message, MessageType.ERROR));
    }

    public void sendMessage(User user, Chats chat, String content, MessageType messageType) {
        ChatMessage message = createChatMessage(user, chat, content, messageType);
        chat.getMessages().add(message);
        chatsRepository.save(chat);

        messagingTemplate.convertAndSend(getChatPath(chat.getId()), message);
    }

    private ChatMessage createChatMessage(User user, Chats chat, String content, MessageType messageType) {
        return new ChatMessage(
                UUID.randomUUID().toString(),
                chat.getId(),
                user.getId(),
                content,
                user.getName(),
                generate.utcDate(),
                messageType);
    }

    private String getChatPath(String chatId) {
        return path + chatId;
    }

}
