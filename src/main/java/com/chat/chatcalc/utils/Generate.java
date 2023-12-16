package com.chat.chatcalc.utils;

import com.chat.chatcalc.reporsitory.ChatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Component
@AllArgsConstructor
public class Generate {

    private final ChatsRepository chatsRepository;
    private final Random random = new Random();

    public String generateRandomID(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomID = new StringBuilder();


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

            // Verifica a unicidade no banco de dados
            isUnique = !chatsRepository.existsByRoomId(roomId);
        } while (!isUnique);

        return roomId;
    }

    public BigDecimal calculatePercentage(BigDecimal part, BigDecimal whole) {
        BigDecimal hundred = new BigDecimal("100.0");

        return part.divide(whole, 4, RoundingMode.HALF_UP).multiply(hundred);
    }

    public Long utcDate(){

        return System.currentTimeMillis();
    }
}
