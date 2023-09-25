package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
       Optional<User> user =  userRepository.findById(userId);
        if (user.isPresent()) {
            return  chatsRepository.findAllByParticipantsUserId(userId);
        }else {
            throw new NotFoundException("Você ainda não tem chats!");
        }
    }
    public Optional<Chats> getChatById(String userId) {
        Optional<Chats> chat =  chatsRepository.findById(userId);
        if (chat.isPresent()) {
            return  chat;
        }else {
            throw new NotFoundException("Não foi possível encontrar o chat!");
        }
    }
}
