package com.chat.chatcalc.service.websocket.cost;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.model.product.CostData;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import com.chat.chatcalc.service.mappers.CostDataMapper;
import com.chat.chatcalc.utils.Generate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CostsService {
    private final ChatsRepository chatsRepository;
    private final WebSocketService webSocketService;
    private final SimpMessagingTemplate messagingTemplate;
    private final CostDataMapper costDataMapper;
    private final Generate generate;
    private final UserRepository userRepository;

    @Value("${websocket.uri.prefix}")
    private String path;



    public void addCostToChat(CostData costData) {
        Chats chat = chatsRepository.findById(costData.getChatId()).orElse(null);

        if (chat == null) {
            webSocketService.errorMessageByChatId(costData.getChatId(), "Chat not found");
            return;
        }

        User user = userRepository.findById(costData.getUserId()).orElse(null);
        if (user == null) {
            webSocketService.errorMessageByChatId(costData.getChatId(), "User not found");
            return;
        }

        Costs lastCost = chat.getCosts();

        if (lastCost == null) {
           chat.setCosts(costDataMapper.mapToCosts(costData, user));
        } else {
            updateLastCost(lastCost, costData, user);
        }

        chatsRepository.save(chat);

        messagingTemplate.convertAndSend(path + costData.getChatId(), lastCost != null ? lastCost : chat.getCosts());
    }

    private void updateLastCost(Costs lastCost, CostData costData, User user) {
        BigDecimal newTotal = costData.getCost().multiply(costData.getQuantity()).add(lastCost.getTotal());
        lastCost.setTotal(newTotal);
        lastCost.setBalance(costData.getTotalSpend().subtract(newTotal));
        lastCost.setPercents(generate.calculatePercentage(newTotal, costData.getTotalSpend()));
        lastCost.getProducts().add(costDataMapper.mapToProducts(costData, user));
    }
}