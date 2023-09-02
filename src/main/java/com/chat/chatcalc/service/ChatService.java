package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.ChatReference;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatsRepository chatsRepository;



    public ChatMessage createChat(CreateRoom createRoom, SimpMessageHeaderAccessor headerAccessor) {
        User user = userRepository
                .findById(createRoom.getUser_id())
                .orElse(null);

        if (user != null) {
            Chats chat = saveChat(user, createRoom, headerAccessor);

            ChatMessage chatMessage = createChatMessage("Sala criada com sucesso", chat, user );

            headerAccessor.getSessionAttributes().put("username", user.getUsername());

            return chatMessage;
        }

        return null;
    }

    private ChatMessage createChatMessage(String content, Chats chat, User user) {
        return new ChatMessage(
                UUID.randomUUID().toString(),
                chat.get_id(),
                content,
                user.getUsername(),
                new Date().toString(),
                MessageType.JOIN);
    }

    private Chats saveChat(User user, CreateRoom createRoom, SimpMessageHeaderAccessor headerAccessor) {
        // seta novo participante
        Participants newParticipant =
                new Participants(user.get_id(), "creator");

        // seta novo chat
        Chats newChat = new Chats(
                UUID.randomUUID().toString(),
                createRoom.getTitle(),
                headerAccessor.getSessionId(),
                Collections.singletonList(newParticipant));

        // cria novo chat
        Chats savedChat = chatsRepository.save(newChat);

        // referencia chat dentro do user
        ChatReference chatReference = new ChatReference(
                savedChat.get_id(),
                "creator"
                );

        user.getChats().add(chatReference);
        // cria novo chat
        userRepository.save(user);

        return savedChat;
    }

}