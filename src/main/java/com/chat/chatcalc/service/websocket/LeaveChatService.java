package com.chat.chatcalc.service.websocket;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.room.LeaveChat;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class LeaveChatService {

    private final ChatsRepository chatsRepository;
    private final UserRepository userRepository;
    private final WebSocketService webSocketService;

    public void leaveChat(LeaveChat leaveChat) {
        Chats chat = chatsRepository.findById(leaveChat.getChatId()).orElse(null);
        User user = userRepository.findById(leaveChat.getUserId()).orElse(null);

        if (user == null && chat != null) {
            webSocketService.errorMessageByChatId(chat.getId(), "Usuário não encontrado");
            throw new NotFoundException("User não encontrado!");
        }

        if (chat != null) {
            removeParticipant(leaveChat.getUserId(), chat);

            if (!isCreatorPresent(chat.getParticipants())) {
                setFirstParticipantAsCreator(chat);
            }

            if (chat.getParticipants().isEmpty()) {
                chatsRepository.deleteById(leaveChat.getChatId());
            } else {
                chatsRepository.save(chat);
                webSocketService.sendMessage(user, chat, user.getName() + " Saiu!", MessageType.LEAVE);
                removeChatFromUser(leaveChat.getUserId(), leaveChat.getChatId());
            }


        } else {
            throw new NotFoundException("Chat não encontrado!");
        }

    }

    private void removeParticipant(String userId, Chats chat) {
        chat.getParticipants().removeIf(participant -> participant.getUserId().equals(userId));
    }

    private void setFirstParticipantAsCreator(Chats chat) {
        if (!chat.getParticipants().isEmpty()) {
            chat.getParticipants().get(0).setRole("creator");
        }
    }

    private boolean isCreatorPresent(List<Participants> participants) {
        return participants.stream().anyMatch(p -> "creator".equalsIgnoreCase(p.getRole()));
    }

    private void removeChatFromUser(String userId, String chatId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getChats().removeIf(chats -> chats.getChatId().equals(chatId));
            userRepository.save(user);
        }
    }
}
