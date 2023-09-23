package com.chat.chatcalc.utils;

import com.chat.chatcalc.reporsitory.ChatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Generate {
    @Autowired
    private ChatsRepository chatsRepository;

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
