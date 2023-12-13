package com.chat.chatcalc.utils;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import org.springframework.stereotype.Component;

@Component
public class DataRetrievalUtil {
    private final UserRepository userRepository;
    private final ChatsRepository chatsRepository;
    private final WebSocketService webSocketService;

    public DataRetrievalUtil(UserRepository userRepository, ChatsRepository chatsRepository, WebSocketService webSocketService) {
        this.userRepository = userRepository;
        this.chatsRepository = chatsRepository;
        this.webSocketService = webSocketService;
    }

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
