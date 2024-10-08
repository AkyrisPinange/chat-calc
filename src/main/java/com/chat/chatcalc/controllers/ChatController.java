package com.chat.chatcalc.controllers;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.model.SuccessResponse;
import com.chat.chatcalc.model.room.CreateRoom;
import com.chat.chatcalc.model.room.LeaveChat;
import com.chat.chatcalc.service.ChatService;
import com.chat.chatcalc.service.CreateChatService;
import com.chat.chatcalc.service.websocket.LeaveChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/")
public class ChatController {

    private final ChatService chatService;
    private final CreateChatService createRoomServiceWs;
    private final LeaveChatService leaveChatService;

    @PostMapping("createChat") // endpoint Api
    public  ResponseEntity<SuccessResponse<Chats>> createRoom(
            @Valid @RequestBody CreateRoom createRoom
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("201", "Chat criado com sucesso", createRoomServiceWs.createChat(createRoom)));

    }

    @PostMapping("leave") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> deleteChat(
            @Valid @RequestBody final LeaveChat leaveChat
    ) {
        leaveChatService.leaveChat(leaveChat);
        return ResponseEntity.ok(new SuccessResponse<>("200", "User removed successfully!", "Sucesso!"));
    }

    @GetMapping("chatByRoomId") // endpoint Api
    public ResponseEntity<SuccessResponse<Chats>> getRoom(
            @RequestParam(value = "roomId") final String roomId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chat Encontrado!", chatService.getChatByRoomId(roomId)));
    }

    @GetMapping("chatsByIdUser") // endpoint Api
    public ResponseEntity<SuccessResponse<List<Chats>>> getChats(
            @RequestParam(value = "userId") final String userId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chats Encontrados!", chatService.getChatsByIdUser(userId)));
    }

    @GetMapping("chatById") // endpoint Api
    public ResponseEntity<SuccessResponse<Optional<Chats>>> getChat(
            @RequestParam(value = "chatId") final String chatId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Conteúdo do chat Carregado!",  chatService.getChatById(chatId)));
    }


}
