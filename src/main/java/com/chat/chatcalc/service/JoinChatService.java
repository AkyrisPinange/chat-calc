package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.ChatReference;
import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.JoinRoom;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JoinChatService {

    private final ChatsRepository chatsRepository;
    private final UserRepository userRepository;
    private final WebSocketService webSocketService;

    @Autowired
    public JoinChatService(ChatsRepository chatsRepository, UserRepository userRepository, WebSocketService webSocketService) {
        this.chatsRepository = chatsRepository;
        this.userRepository = userRepository;
        this.webSocketService = webSocketService;
    }

    public void joinChat(JoinRoom joinRoom) {
        Chats chat = getChatById(joinRoom.getChatId());
        User user = getUserById(joinRoom.getUserId(), joinRoom.getChatId());

        if (chat != null && user != null) {
            if (!isUserParticipant(chat, user)) {
                Participants newParticipant = new Participants(joinRoom.getUserId(), "participant");
                chat.getParticipants().add(newParticipant);
                chatsRepository.save(chat);

                ChatReference chatReference = new ChatReference(chat.getId(), "participant");
                user.getChats().add(chatReference);
                userRepository.save(user);

                webSocketService.joinChatMessage(chat, user,  user.getUsername() + " entrou!");
            } else {
                webSocketService.errorMessageByChatId(chat.getId(), "Usuário já participa do chat");
            }
        }
    }

    private Chats getChatById(String chatId) {
        return chatsRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundException("Chat não foi encontrado"));
    }

    private User getUserById(String userId, String chatId) {
        return userRepository.findById(userId)
                .orElseGet(() -> {
                    webSocketService.errorMessageByChatId(chatId, "Usuário Chat não foi encontrado");
                    return null;
                });
    }

    private boolean isUserParticipant(Chats chat, User user) {
        return chat.getParticipants().stream()
                .anyMatch(participant -> participant.getUserId().equals(user.getId()));
    }
}