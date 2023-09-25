package com.chat.chatcalc.controllers;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.model.SuccessResponse;
import com.chat.chatcalc.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat/getChatByRoomId") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<Chats>> getRoom(
            @RequestParam(value = "roomId") final String roomId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chat Encontrado!", chatService.getChatByRoomId(roomId)));
    }

    @GetMapping("/chat/getChatsByIdUser") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<Chats>>> getChats(
            @RequestParam(value = "userId") final String userId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chats Encontrados!", chatService.getChatsByIdUser(userId)));
    }

    @GetMapping("/chat/getChatById") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<Optional<Chats>>> getChat(
            @RequestParam(value = "chatId") final String chatId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Conteúdo do chat Carregado!",  chatService.getChatById(chatId)));
    }
}
