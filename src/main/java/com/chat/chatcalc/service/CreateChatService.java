package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.room.CreateRoom;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.utils.ChatManipulationUtil;
import com.chat.chatcalc.utils.Generate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChatService {

    private final UserRepository userRepository;
    private final ChatManipulationUtil chatManipulationUtil;
    private final Generate generate;

    public Chats createChat(CreateRoom createRoom) {
        User user = userRepository.findById(createRoom.getUserId()).orElse(null);

        if (user == null) {
            throw new NotFoundException("Impossible to create chat, user not found");
        }

        if (createRoom.getTitle().isEmpty()) {
            throw new NotFoundException("Impossible to create chat, title must not be empty");
        }

        return saveChat(user, createRoom);

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