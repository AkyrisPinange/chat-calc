package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Participants;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.room.DeleteChat;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatsRepository chatsRepository;
    private final UserRepository userRepository;

    public ChatService(ChatsRepository chatsRepository, UserRepository userRepository) {
        this.chatsRepository = chatsRepository;
        this.userRepository = userRepository;
    }

    public Chats getChatByRoomId(String roomId) {
        Chats chat = chatsRepository.getChatByRoomId(roomId);
        if (chat == null) {
            throw new NotFoundException("Chat não encontrado!");
        }
        return new Chats(chat.getId(), chat.getRoomName());
    }

    public List<Chats> getChatsByIdUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return chatsRepository.findAllByParticipantsUserId(userId);
        } else {
            throw new NotFoundException("Você ainda não tem chats!");
        }
    }

    public Optional<Chats> getChatById(String userId) {
        Optional<Chats> chat = chatsRepository.findById(userId);
        if (chat.isPresent()) {
            return chat;
        } else {
            throw new NotFoundException("Não foi possível encontrar o chat!");
        }
    }

    public String deleteChat(DeleteChat deleteChat) {
        Chats chat = chatsRepository.findById(deleteChat.getChatId()).orElse(null);

        if (chat == null) {
            return "Chat not found";
        }

        removeParticipant(deleteChat.getUserId(), chat);

        if (!isCreatorPresent(chat.getParticipants())) {
            setFirstParticipantAsCreator(chat);
        }

        if (chat.getParticipants().isEmpty()) {
            chatsRepository.deleteById(deleteChat.getChatId());
        } else {
            chatsRepository.save(chat);
        }

        removeChatFromUser(deleteChat.getUserId(), deleteChat.getChatId());

        return "User exited the chat";
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
