package com.chat.chatcalc.controllers;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.model.SuccessResponse;
import com.chat.chatcalc.service.ChatService;
import com.chat.chatcalc.service.CreateChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/")
public class ChatController {

    private final ChatService chatService;
    private final CreateChatService createRoomServiceWs;


    @PostMapping("createChat") // endpoint Api
    public  ResponseEntity<SuccessResponse<Chats>> createRoom(
            @RequestBody CreateRoom createRoom
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("201", "Chat criado com sucesso", createRoomServiceWs.createChat(createRoom)));

    }

    @GetMapping("getChatByRoomId") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<Chats>> getRoom(
            @RequestParam(value = "roomId") final String roomId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chat Encontrado!", chatService.getChatByRoomId(roomId)));
    }

    @GetMapping("getChatsByIdUser") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<Chats>>> getChats(
            @RequestParam(value = "userId") final String userId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chats Encontrados!", chatService.getChatsByIdUser(userId)));
    }

    @GetMapping("getChatById") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<Optional<Chats>>> getChat(
            @RequestParam(value = "chatId") final String chatId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Conteúdo do chat Carregado!",  chatService.getChatById(chatId)));
    }
}
