package com.chat.chatcalc.chat;


import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.model.CreateRoom;
import com.chat.chatcalc.model.JoinRoom;
import com.chat.chatcalc.model.SendMessage;
import com.chat.chatcalc.service.ChatService;

import com.chat.chatcalc.service.JoinChatService;
import com.chat.chatcalc.service.PriceService;
import com.chat.chatcalc.service.SendMesageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final PriceService priceService;
    private final ChatService createRoomService;
    private final JoinChatService joinChatService;
    private final SendMesageService sendMessageService;

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public ChatMessage sendMessage(SendMessage sendMessage){
                return sendMessageService.sendMesage(sendMessage);
    }

    @MessageMapping("/chat.sendPrice")
    @SendTo("/topic/public")// endpoint webSocket
    public ChatMessage sendPrice(@Payload ChatMessage chatMessage){
        return  priceService.calcPrice(chatMessage);
    }

    @MessageMapping("/chat/{chatId}/joinChat")
    @SendTo("/topic/chat/{chatId}")
    public ChatMessage joinChat(
            JoinRoom joinRoom,
            SimpMessageHeaderAccessor headerAccessor
    ) throws Exception {


        return joinChatService.joinChat(joinRoom);
    }

    @MessageMapping("/chat.createChat")
    @SendTo("/topic/public")// endpoint webSocket
    public ChatMessage creatRoom(
            @Payload CreateRoom createRoom,
            SimpMessageHeaderAccessor headerAccessor
    ) {

        return createRoomService.createChat(createRoom, headerAccessor);
    }

}
