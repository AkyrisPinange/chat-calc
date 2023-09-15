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

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat/getChatByRoomId") // endpoint Api
    @ResponseBody
    public ResponseEntity<SuccessResponse<Chats>> createRoom(
            @RequestParam(value = "roomId") final String roomId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Chat Encontrado!", chatService.getChatByRoomId(roomId)));
    }
}
