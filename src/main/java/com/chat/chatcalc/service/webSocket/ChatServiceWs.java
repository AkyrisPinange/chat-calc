package com.chat.chatcalc.service.webSocket;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import com.chat.chatcalc.utils.ChatManipulationUtil;
import com.chat.chatcalc.utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceWs {
    private final UserRepository userRepository;
    private final ChatManipulationUtil chatManipulationUtil;
    private final Generate generate;
    private final WebSocketService webSocketService;

    @Autowired
    public ChatServiceWs(UserRepository userRepository, ChatManipulationUtil chatManipulationUtil, Generate generate, WebSocketService webSocketService) {
        this.userRepository = userRepository;
        this.chatManipulationUtil = chatManipulationUtil;
        this.generate = generate;
        this.webSocketService = webSocketService;
    }

    public void createChat(CreateRoom createRoom) {
        User user = userRepository.findById(createRoom.getUserId()).orElse(null);

        if (user == null) {
            webSocketService.errorMessageByUser(createRoom.getUserId(), "Impossible to create chat, user not found");
            return;
        }

        if (createRoom.getTitle().isEmpty()) {
            webSocketService.errorMessageByUser(createRoom.getUserId(), "Impossible to create chat, title must not be empty");
            return;
        }

        Chats chat = saveChat(user, createRoom);
        webSocketService.createChatMessage(chat, user, "Sala criada com sucesso");
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


}