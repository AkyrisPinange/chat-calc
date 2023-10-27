package com.chat.chatcalc.service.webSocket;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.entiteis.Products;
import com.chat.chatcalc.model.CostData;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.service.WebSocketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    public void addCostToChat(CostData costData) {
        Chats chat = chatsRepository.findById(costData.getChatId()).orElse(null);
        if (chat != null) {
            Costs lastCost = updateLastCost(chat, costData);
            if (lastCost == null) {
                createNewCost(chat, costData);
            }
            chatsRepository.save(chat);
            messagingTemplate.convertAndSend(path + costData.getChatId(), lastCost != null ? lastCost : chat.getCosts());
        } else {
            webSocketService.errorMessageByChatId(costData.getChatId(), "Chat not found");
        }
    }

    private Costs updateLastCost(Chats chat, CostData costData) {
        List<Costs> costsList = chat.getCosts();
        Costs lastCost = costsList.isEmpty() ? null : costsList.get(costsList.size() - 1);

        if (lastCost != null) {
            BigDecimal previousTotal = lastCost.getTotal();
            BigDecimal newTotal = costData.getCost().multiply(costData.getQuantity()).add(previousTotal);
            String newPercent = calculatePercentage(newTotal, costData.getTotalSpend());
            lastCost.setTotal(newTotal);
            lastCost.setPercents(newPercent);
            addNewProduct(lastCost, costData);
        }
        return lastCost;
    }

    private void createNewCost(Chats chat, CostData costData) {
        Costs newCost = createCostFromData(costData, BigDecimal.ZERO);
        chat.getCosts().add(newCost);
    }

    private void addNewProduct(Costs cost, CostData costData) {
        Products newProduct = new Products();
        newProduct.setQuantity(costData.getQuantity());
        newProduct.setCost(costData.getCost());
        newProduct.setId(UUID.randomUUID().toString());
        newProduct.setProduct(costData.getProduct());
        cost.getProducts().add(newProduct);
    }

    private Costs createCostFromData(CostData costData, BigDecimal previousTotal) {
        BigDecimal total = costData.getCost().multiply(costData.getQuantity()).add(previousTotal);
        String percent = calculatePercentage(total, costData.getTotalSpend());
        Costs cost = new Costs();
        Products products = new Products();
        products.setQuantity(costData.getQuantity());
        products.setCost(costData.getCost());
        products.setId(UUID.randomUUID().toString());
        products.setProduct(costData.getProduct());
        cost.setTotalSpend(costData.getTotalSpend());
        cost.setPercents(percent);
        cost.setTotal(total);
        cost.setChatId(costData.getChatId());
        cost.getProducts().add(products);
        return cost;
    }

    private String calculatePercentage(BigDecimal part, BigDecimal whole) {
        BigDecimal hundred = new BigDecimal("100.0");
        BigDecimal percentage = part.divide(whole, 4, BigDecimal.ROUND_HALF_UP).multiply(hundred);
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        return decimalFormat.format(percentage) + "%";
    }
}
