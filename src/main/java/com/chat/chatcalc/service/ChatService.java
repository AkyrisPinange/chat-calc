package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.utils.Generate;
import com.chat.chatcalc.utils.ChatManipulationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final UserRepository userRepository;
    private final ChatManipulationUtil chatManipulationUtil;
    private final Generate generate;
    private final WebSocktService webSocktService;
    private final ChatsRepository chatsRepository;

    @Autowired
    public ChatService(UserRepository userRepository, ChatManipulationUtil chatManipulationUtil, WebSocktService webSocktService,
                       ChatsRepository chatsRepository, Generate generate) {
        this.userRepository = userRepository;
        this.chatManipulationUtil = chatManipulationUtil;
        this.webSocktService = webSocktService;
        this.chatsRepository = chatsRepository;
        this.generate = generate;
    }

    public void createChat(CreateRoom createRoom) {
        User user = userRepository.findById(createRoom.getUserId()).orElse(null);

        if (user == null) {
            webSocktService.errorMessageByUser(createRoom.getUserId(), "Impossible to create chat, user not found");
            return;
        }

        if (createRoom.getTitle().isEmpty()) {
            webSocktService.errorMessageByUser(createRoom.getUserId(), "Impossible to create chat, title must not be empty");
            return;
        }

        Chats chat = saveChat(user, createRoom);
        webSocktService.createChatMessage(chat, user, "Sala criada com sucesso");
    }

    private Chats saveChat(User user, CreateRoom createRoom) {
        String title = createRoom.getTitle();
        String uniqueRoomId = generate.generateUniqueRoomId();
        String userId = user.getId();
        String role = "creator";

        // Create a new chat
        Chats newChat = chatManipulationUtil.createNewChat(title, uniqueRoomId, userId, role);

        // Reference the new chat in the user
        chatManipulationUtil.addNewChatToUser(user, newChat.getId(), role);

        return newChat;
    }

    public Chats getChatByRoomId(String roomId) {
        Chats chat = chatsRepository.getChatByRoomId(roomId);
        if (chat == null) {
            throw new NotFoundException("Chat not found");
        }
        return new Chats(chat.getId(), chat.getRoomName());
    }
}