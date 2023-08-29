package com.chat.chatcalc.chat;


import com.chat.chatcalc.model.ChatMessage;
import com.chat.chatcalc.service.PriceService;
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

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")// endpoint webSocket
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
                return chatMessage;
    }
    @MessageMapping("/chat.sendPrice")
    @SendTo("/topic/public")// endpoint webSocket
    public ChatMessage sendPrice(@Payload ChatMessage chatMessage){
        return  priceService.calcPrice(chatMessage);
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")// endpoint webSocket
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in webSocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return  chatMessage;
    }

}
