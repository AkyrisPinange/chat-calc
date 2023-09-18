package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.utils.Useful;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final UserRepository userRepository;
    private final Useful useful;
    private final WebSocktService webSocktService;
    private final ChatsRepository chatsRepository;

    @Autowired
    public ChatService(UserRepository userRepository, Useful useful, WebSocktService webSocktService, ChatsRepository chatsRepository) {
        this.userRepository = userRepository;
        this.useful = useful;
        this.webSocktService = webSocktService;
        this.chatsRepository = chatsRepository;
    }

    public void createChat(CreateRoom createRoom, SimpMessageHeaderAccessor headerAccessor) {
        User user = userRepository.findById(createRoom.getUserId()).orElse(null);

        if (user != null) {
            Chats chat = saveChat(user, createRoom);
            webSocktService.createChatMessage(chat, user, "Sala criada com sucesso");
        } else if (createRoom.getTitle().isEmpty()) {
            webSocktService.errorMessageByUser(createRoom.getUserId(), "impossible to create chat, title not must be empty");
        } else {
            webSocktService.errorMessageByUser(createRoom.getUserId(), "impossible to create chat, user not found");
        }
    }

    private Chats saveChat(User user, CreateRoom createRoom) {
        // Create a new chat
        Chats newChat = useful.createNewChat(
                createRoom.getTitle(),
                useful.generateUniqueRoomId(),
                user.getId(),
                "creator"
        );

        // Reference the new chat in the user
        User newChatToUser = useful.addNewChatToUser(
                user,
                newChat.getId(),
                "creator"
        );

        return newChat;
    }

    public Chats getChatByRoomId(String roomId) {

        Chats chat = chatsRepository.getChatByRoomId(roomId);
        if (chat != null) {
            return new Chats(chat.getId(), chat.getRoomName());
        } else {
            throw new NotFoundException("Chat not found");
        }

    }
}