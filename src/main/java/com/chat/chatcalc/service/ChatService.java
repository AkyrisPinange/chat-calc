package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.ChatReference;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.handler.exceptions.UserNotFoundException;
import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.utils.Useful;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Useful useful;

    @Autowired
    private ChatsRepository chatsRepository;


    public ChatMessage createChat(CreateRoom createRoom, SimpMessageHeaderAccessor headerAccessor) {
        User user = userRepository
                .findById(createRoom.getUser_id())
                .orElse(null);


        if (user != null) {
            Chats chat = saveChat(user, createRoom);

            ChatMessage chatMessage = createChatMessage("Sala criada com sucesso", chat, user);

            headerAccessor.getSessionAttributes().put("username", user.getUsername());

            return chatMessage;
        } else {
            throw new UserNotFoundException("impossible to create chat, user not found");
        }


    }

    private ChatMessage createChatMessage(String content, Chats chat, User user) {
        return new ChatMessage(
                UUID.randomUUID().toString(),
                chat.getId(),
                chat.getRoom_id(),
                content,
                user.getUsername(),
                new Date().toString(),
                MessageType.JOIN);
    }

    private Chats saveChat(User user, CreateRoom createRoom) {

        // create novo chat
        Chats newChat =
                useful.createNewChat(
                        createRoom.getTitle(),
                        useful.generateRandomID(7) ,
                        user.getId(),
                        "creator");

        // reference new chat into user
        User newChatToUser =
                useful.addNewChatToUser(
                        user,
                        newChat.getId(),
                        "creator");
        return newChat;
    }



}