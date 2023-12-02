package com.chat.chatcalc.utils;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import org.springframework.stereotype.Component;

@Component
public class DataRetrievalUtil {
    private UserRepository userRepository;
    private ChatsRepository chatsRepository;
    private WebSocketService webSocketService;

    public Chats getChat(String chatId) {
        return chatsRepository.findById(chatId)
                .orElseGet(() -> {
                    webSocketService.errorMessageByChatId(chatId, "User not found");
                    return null;
                });
    }

    public User getUser(String userId, String chatId) {
        return userRepository.findById(userId)
                .orElseGet(() -> {
                    webSocketService.errorMessageByChatId(chatId, "User not found");
                    return null;
                });
    }


}
