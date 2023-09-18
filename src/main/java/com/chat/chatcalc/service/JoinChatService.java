package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.ChatReference;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.model.JoinRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JoinChatService {

    private final ChatsRepository chatsRepository;
    private final UserRepository userRepository;
    private final WebSocktService webSocktService;

    @Autowired
    public JoinChatService(ChatsRepository chatsRepository, UserRepository userRepository, WebSocktService webSocktService) {
        this.chatsRepository = chatsRepository;
        this.userRepository = userRepository;
        this.webSocktService = webSocktService;
    }

    public void joinChat(JoinRoom joinRoom) {
        Chats chat = chatsRepository.findById(joinRoom.getChatId()).orElse(null);

        assert chat != null;

        boolean isParticipant = isUserParticipant(chat.getRoomId(), joinRoom.getUserId());

        if (!isParticipant) {
            Participants newParticipant = new Participants(joinRoom.getUserId(), "participant");
            chat.getParticipants().add(newParticipant);
            chatsRepository.save(chat);

            ChatReference chatReference = new ChatReference(chat.getId(), "participant");
            User user = userRepository.findById(joinRoom.getUserId()).orElse(null);

            if (user != null) {
                user.getChats().add(chatReference);
                userRepository.save(user);
                webSocktService.joinChatMessage(chat, user, "User :" + user.getUsername() + " join!");
            } else {
                webSocktService.errorMessageByChatId(chat.getId(), "User not found");
            }
        } else {
            webSocktService.errorMessageByChatId(chat.getId(), "User is already a participant in this chat");
        }
    }

    private Boolean isUserParticipant(String chatId, String userId) {
        return chatsRepository.findByRoomIdAndParticipantUserId(chatId, userId) != null;
    }
}
