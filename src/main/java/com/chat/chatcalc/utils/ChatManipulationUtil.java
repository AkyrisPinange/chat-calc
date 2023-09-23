package com.chat.chatcalc.utils;

import com.chat.chatcalc.entiteis.ChatReference;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class ChatManipulationUtil {
    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private UserRepository userRepository;

    public Participants newParticipant(String user_id, String role) {
        return new Participants(user_id, role);
    }

    // save
    public Chats createNewChat(String roomName,// title chat
                               String roomId, // session id
                               String userId, // id chat
                               String role // creator role
    ) {
        Participants newParticipant = new Participants(userId, role);
        Chats newChat = new Chats(UUID.randomUUID().toString(), // random id
                roomName, roomId, Collections.singletonList(newParticipant));

        return chatsRepository.save(newChat);
    }

    public void addNewChatToUser(User user, // User who will enter the chat
                                 String chat_id, // id the chat
                                 String role // role new user
    ) {
        ChatReference newChatReference = new ChatReference(chat_id, role);

        user.getChats().add(newChatReference);

        userRepository.save(user);
    }


}
