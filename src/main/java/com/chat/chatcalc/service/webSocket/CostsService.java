package com.chat.chatcalc.service.webSocket;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.service.WebSocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CostsService {
    private final ChatsRepository chatsRepository;
    private final WebSocketService webSocketService;
    private final SimpMessagingTemplate messagingTemplate;
    @Value("${websocket.uri.prefix}")
    private String path;

    public CostsService(ChatsRepository chatsRepository,
                        WebSocketService webSocketService,
                        SimpMessagingTemplate messagingTemplate) {
        this.chatsRepository = chatsRepository;
        this.webSocketService = webSocketService;
        this.messagingTemplate = messagingTemplate;
    }

    public void addCostToChat(Costs costData) {
        Chats chat = chatsRepository.findById(costData.getChatId()).orElse(null);
        if (chat != null) {
            BigDecimal previousTotal = getLastTotal(chat);

            Costs cost = createCostFromData(costData, previousTotal);

            chat.getCosts().add(cost);
            chatsRepository.save(chat);

            messagingTemplate.convertAndSend(path + costData.getChatId(), cost);
        } else {
            webSocketService.errorMessageByChatId(costData.getChatId(), "Chat not found");
        }
    }

    private BigDecimal getLastTotal(Chats chat) {
        List<Costs> costsList = chat.getCosts();
        Costs lastCost = costsList.isEmpty() ? null : costsList.get(costsList.size() - 1);

        return lastCost != null ?  lastCost.getTotal()  : BigDecimal.ZERO;
    }

    private Costs createCostFromData(Costs costData, BigDecimal previousTotal) {
        BigDecimal total = costData.getCost().multiply(costData.getQuantity())
                .add(previousTotal);

        Costs cost = new Costs();
        cost.setQuantity(costData.getQuantity());
        cost.setCost(costData.getCost());
        cost.setTotal(total);
        cost.setProduct(costData.getProduct());
        cost.setChatId(costData.getChatId());
        cost.setId(UUID.randomUUID().toString());

        return cost;
    }


}
