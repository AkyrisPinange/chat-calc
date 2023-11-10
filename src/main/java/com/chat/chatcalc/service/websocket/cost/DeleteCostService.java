package com.chat.chatcalc.service.websocket.cost;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.entiteis.Products;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.DeleteCost;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.utils.Generate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class DeleteCostService {
    @Value("${websocket.uri.prefix}")
    private String path;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatsRepository chatsRepository;
    private final MongoTemplate mongoTemplate;
    private Generate generate;

    public DeleteCostService(SimpMessagingTemplate messagingTemplate, MongoTemplate mongoTemplate, ChatsRepository chatsRepository,  Generate generate) {
        this.messagingTemplate = messagingTemplate;
        this.chatsRepository = chatsRepository;
        this.mongoTemplate = mongoTemplate;
        this.generate =  generate;
    }

    public void deleteProduct(DeleteCost deleteCost) {
        Query query = new Query(Criteria.where("_id").is(deleteCost.getChatId()));
        Update update = new Update().pull("costs.products", new Query(Criteria.where("_id").is(deleteCost.getProductId())));

        mongoTemplate.updateFirst(query, update, Chats.class);

        // Fetch the updated chat document
        Chats updatedChat = mongoTemplate.findOne(query, Chats.class);

        // Check if the chat document is not null before proceeding
        if (updatedChat != null) {
            // Calculate new total, percents, and balance
            Costs costs = updatedChat.getCosts();
            BigDecimal totalSpend = costs.getTotalSpend();
            List<Products> products = costs.getProducts();

            BigDecimal newTotal = BigDecimal.ZERO;
            for (Products product : products) {
                newTotal = newTotal.add(product.getCost().multiply(product.getQuantity()));
            }

            costs.setTotal(newTotal);
            costs.setPercents(generate.calculatePercentage(newTotal, totalSpend));
            costs.setBalance(totalSpend.subtract(newTotal));

            // Save the updated chat document with the recalculated values
            chatsRepository.save(updatedChat);

            // Send the updated costs to the messaging template
            messagingTemplate.convertAndSend(path + updatedChat.getId(), costs);
        } else {
            throw new NotFoundException("Não foi encontrado chat ou produto");
        }
    }



}
