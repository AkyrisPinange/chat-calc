package com.chat.chatcalc.service.mappers;

import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import com.chat.chatcalc.utils.Generate;
import org.springframework.stereotype.Component;

import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.entiteis.Products;
import com.chat.chatcalc.model.CostData;
import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CostDataMapper {

    private final Generate generate;

    public CostDataMapper(Generate generate) {
        this.generate = generate;
    }

    public Costs mapToCosts(CostData costData, User user) {

        BigDecimal total = costData.getCost().multiply(costData.getQuantity());
        Costs costs = new Costs();
        costs.setTotalSpend(costData.getTotalSpend());
        costs.setPercents(generate.calculatePercentage(total, costData.getTotalSpend()));
        costs.setTotal(total);
        costs.setChatId(costData.getChatId());
        costs.setType(MessageType.PRICE);
        costs.getProducts().add(mapToProducts(costData, user));
        return costs;
    }

    public Products mapToProducts(CostData costData, User user) {
        Products product = new Products();
        product.setQuantity(costData.getQuantity());
        product.setCost(costData.getCost());
        product.setId(UUID.randomUUID().toString());
        product.setProduct(costData.getProduct());
        product.setName(user.getName());
        product.setUserId(user.getId());
        return product;
    }

}
