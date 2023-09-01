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

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatsRepository chatsRepository;

    public ChatMessage createChat(CreateRoom createRoom, SimpMessageHeaderAccessor headerAccessor) {
        Optional<User> userOptional = userRepository.findById(createRoom.getUser_id());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Chats chat = saveChat(user, createRoom);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent("Sala criada com sucesso");
            chatMessage.setChat_id(chat.get_id());
            chatMessage.set_id(UUID.randomUUID().toString());
            chatMessage.setUsername(user.getUsername());
            chatMessage.setTimestemp(new Date().toString());
            chatMessage.setType(MessageType.JOIN);

            headerAccessor.getSessionAttributes().put("username", user.getUsername());

            return chatMessage;
        }

        return null;
    }

    private Chats saveChat(User user, CreateRoom createRoom) {
        Participants newParticipant = new Participants();
        newParticipant.setRole("creator");
        newParticipant.setId_user(user.get_id());

        Chats newChat = new Chats();
        newChat.set_id(UUID.randomUUID().toString());
        newChat.setTitle(createRoom.getTitle());
        newChat.getParticipants().add(newParticipant);

        Chats savedChat = chatsRepository.save(newChat);

        ChatReference chatReference = new ChatReference();
        chatReference.setChat_id(savedChat.get_id());
        chatReference.setRole("creator");
        user.getChats().add(chatReference);

        userRepository.save(user);

        return savedChat;
    }
}