package com.chat.chatcalc.utils;

import com.chat.chatcalc.entiteis.ChatMessage;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.SendMessage;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocktService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class DataRetrievalUtil {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatsRepository chatsRepository;
    @Autowired
    private WebSocktService webSocktService;

    public Chats getChat(String chatId) {
        return chatsRepository.findById(chatId)
                .orElseGet(() -> {
                    webSocktService.errorMessageByChatId(chatId, "User not found");
                    return null;
                });
    }

    public User getUser(String userId, String chatId) {
        return userRepository.findById(userId)
                .orElseGet(() -> {
                    webSocktService.errorMessageByChatId(chatId, "User not found");
                    return null;
                });
    }
}
