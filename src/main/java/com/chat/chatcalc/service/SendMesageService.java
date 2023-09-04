package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.model.SendMessage;
import com.chat.chatcalc.reporsitory.ChatMessageRepository;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import de.huxhorn.sulky.ulid.ULID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SendMesageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage sendMesage(SendMessage sendMesage) {

        ULID.Value ulidValue = ULID.parseULID(UUID.randomUUID().toString());
        User user = userRepository.getUserById(sendMesage.getUser_id());


        ChatMessage newMessage = new ChatMessage(
                UUID.randomUUID().toString(),
                sendMesage.getChat_id(),
                sendMesage.getContent(),
                user.getUsername(),
                new Date().toString(),
                MessageType.SEND);

        chatMessageRepository.save(newMessage);

        return newMessage;


    }
}
