package com.chat.chatcalc.utils;

import com.chat.chatcalc.entiteis.*;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Component
public class ChatManipulationUtil {
    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private UserRepository userRepository;


    public Participants newParticipant(String userId, String role) {

        return new Participants(userId, role, LocalDateTime.now());
    }

    // save
    public Chats createNewChat(String roomName,// title chat
                               String roomId, // session id
                               String userId, // id chat
                               String role // creator role
    ) {
        Participants newParticipant = new Participants(userId, role , LocalDateTime.now());
        Costs costs = new Costs(UUID.randomUUID().toString(), BigDecimal.ZERO, new BigDecimal(100),BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>(), MessageType.PRICE );
        Chats newChat = new Chats(UUID.randomUUID().toString(), // random id
                roomName, roomId, Collections.singletonList(newParticipant), costs);



        return chatsRepository.save(newChat);
    }

    public void addNewChatToUser(User user, // User who will enter the chat
                                 String chatId, // id the chat
                                 String role // role new user
    ) {
        ChatReference newChatReference = new ChatReference(chatId, role);

        user.getChats().add(newChatReference);

        userRepository.save(user);
    }


}
