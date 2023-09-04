package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.ChatReference;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.model.JoinRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;
import java.util.UUID;


@Service
public class JoinChatService {

    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatMessage joinChat(JoinRoom joinRoom) throws Exception {

        Chats chat = chatsRepository.findById(joinRoom.getChat_id()).orElse(null);

        Participants newParticipant =
                new Participants(joinRoom.getUser_id(), "participant");

        chat.getParticipants().add(newParticipant);

        chatsRepository.save(chat);

        ChatReference chatReference = new ChatReference(
                chat.getId(),
                "participant"
                );

        User user = userRepository.findById(joinRoom.getUser_id()).orElse(null);
        user.getChats().add(chatReference);

        userRepository.save(user);

        return new ChatMessage(
                UUID.randomUUID().toString(),
                chat.getId(),
                "entou no chat",
                user.getUsername(),
                new Date().toString(),
                MessageType.JOIN);


    }

}
