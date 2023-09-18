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
import java.util.Random;
import java.util.UUID;

@Component
public class Useful {
    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private UserRepository userRepository;

    public Participants newParticipant(
            String user_id,
            String role) {
        return new Participants(user_id, role);
    }

    public Chats createNewChat(
            String roomName,// title chat
            String roomId, // session id
            String userId, // id chat
            String role // creator role
    ) {
        Participants newParticipant = new Participants(userId, role);
        Chats newChat =
                new Chats(
                        UUID.randomUUID().toString(), // random id
                        roomName,
                        roomId,
                        Collections.singletonList(newParticipant));

        return chatsRepository.save(newChat);
    }

    public User addNewChatToUser(
            User user, // User who will enter the chat
            String chat_id, // id the chat
            String role // role new user
    ) {
        ChatReference newChatReference = new ChatReference(
                chat_id,
                role
        );


        user.getChats().add(newChatReference);

        return userRepository.save(user);
    }

    public String generateRandomID(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomID = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomID.append(randomChar);
        }

        return randomID.toString();
    }

    public String generateUniqueRoomId() {
        String roomId;
        boolean isUnique;

        do {
            // Gere um identificador curto aleatório
            roomId = generateRandomID(7);

            // Verifique a unicidade no banco de dados
            isUnique = !chatsRepository.existsByRoomId(roomId);
        } while (!isUnique);

        return roomId;
    }

}
