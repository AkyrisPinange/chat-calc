package com.chat.chatcalc.controllers;


import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.model.JoinRoom;
import com.chat.chatcalc.model.SendMessage;
import com.chat.chatcalc.service.webSocket.CostsService;
import com.chat.chatcalc.service.webSocket.JoinChatServiceWs;
import com.chat.chatcalc.service.webSocket.SendMessageServiceWs;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {


    private final JoinChatServiceWs joinChatServiceWs;
    private final SendMessageServiceWs sendMessageService;
    private final CostsService costsService;

    @MessageMapping("/chat/{chatId}/joinChat") // endpoint Api
    @SendTo("/topic/chat/{chatId}") // endpoint webSocket
    public void joinChat(
            JoinRoom joinRoom,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        joinChatServiceWs.joinChat(joinRoom);
    }

    @MessageMapping("/chat/{chatId}/costs") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void sendCosts(Costs costs) {
        costsService.addCostToChat(costs);
    }

    @MessageMapping("/chat/{chatId}/sendMessage") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void sendMessage(SendMessage sendMessage) {
        sendMessageService.sendMessage(sendMessage);
    }

}
