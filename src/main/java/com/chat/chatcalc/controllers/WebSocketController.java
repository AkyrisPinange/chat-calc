package com.chat.chatcalc.controllers;


import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.model.JoinRoom;
import com.chat.chatcalc.model.SendMessage;
import com.chat.chatcalc.service.webSocket.ChatServiceWs;
import com.chat.chatcalc.service.webSocket.JoinChatServiceWs;
import com.chat.chatcalc.service.webSocket.SendMessageServiceWs;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ChatServiceWs createRoomServiceWs;
    private final JoinChatServiceWs joinChatServiceWs;
    private final SendMessageServiceWs sendMessageService;


    @MessageMapping("/chat/createChat") // endpoint Api
    @SendTo("/topic/chat/") // endpoint webSocket
    public void createRoom(
            @Payload CreateRoom createRoom,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        createRoomServiceWs.createChat(createRoom);
    }

    @MessageMapping("/chat/{chatId}/joinChat") // endpoint Api
    @SendTo("/topic/chat/{chatId}") // endpoint webSocket
    public void joinChat(
            JoinRoom joinRoom,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        joinChatServiceWs.joinChat(joinRoom);
    }

    @MessageMapping("/chat/{chatId}/sendMessage") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void sendMessage(SendMessage sendMessage) {
        sendMessageService.sendMessage(sendMessage);
    }
}
