package com.chat.chatcalc.controllers;


import com.chat.chatcalc.model.*;
import com.chat.chatcalc.service.websocket.*;
import com.chat.chatcalc.service.websocket.cost.ChangeSpendService;
import com.chat.chatcalc.service.websocket.cost.CostsService;
import com.chat.chatcalc.service.websocket.cost.DeleteCostService;
import com.chat.chatcalc.service.websocket.cost.UpdateProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {


    private final JoinChatServiceWs joinChatServiceWs;
    private final SendMessageServiceWs sendMessageService;
    private final CostsService costsService;
    private final ChangeSpendService changeSpendService;
    private final UpdateProductService updateProductService;
    private final DeleteCostService deleteCostService;

    @MessageMapping("/chat/{chatId}/joinChat") // endpoint Api
    @SendTo("/topic/chat/{chatId}") // endpoint webSocket
    public void joinChat(
            JoinRoom joinRoom
    ) {
        joinChatServiceWs.joinChat(joinRoom);
    }

    @MessageMapping("/chat/{chatId}/sendMessage") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void sendMessage(SendMessage sendMessage) {
        sendMessageService.sendMessage(sendMessage);
    }

    @MessageMapping("/chat/{chatId}/costs") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void sendCosts(CostData costs) {
        costsService.addCostToChat(costs);
    }

    @MessageMapping("/chat/{chatId}/changeTotalSpend") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void changeSpend(ChangeSpend changeSpend) {
        changeSpendService.changeSpend(changeSpend);
    }

    @MessageMapping("/chat/{chatId}/updateProduct") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void updateProduct(UpdateProduct updateProduct) {
        updateProductService.updateProduct(updateProduct);
    }

    @MessageMapping("/chat/{chatId}/deleteProduct") // endpoint Api
    @SendTo("/topic/chat/{chatId}")// endpoint webSocket
    public void deleteProduct(
            DeleteCost deleteCost
    ) {
        deleteCostService.deleteProduct(deleteCost);
    }

}
