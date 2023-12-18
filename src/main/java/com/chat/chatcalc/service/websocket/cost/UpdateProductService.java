package com.chat.chatcalc.service.websocket.cost;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.entiteis.Products;
import com.chat.chatcalc.model.product.UpdateProduct;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.utils.Generate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateProductService {
    @Value("${websocket.uri.prefix}")
    private  String path;
    private final ChatsRepository chatsRepository;
    private final SimpMessagingTemplate messagingTemplate;
     private final Generate generate;
    public void updateProduct(UpdateProduct product) {

        Chats chats = chatsRepository.findChatsByProductId(product.getProductId());

        if (chats != null ) {
            Costs costs = chats.getCosts();
            List<Products> productsList = costs.getProducts();

            BigDecimal productCost = product.getCost();
            BigDecimal productQuantity = product.getQuantity();
            String productIdToUpdate = product.getProductId();

            // Use filter to find the product with the matching productId
            Optional<Products> productOptional = productsList.stream()
                    .filter(p -> p.getId().equals(productIdToUpdate))
                    .findFirst();

            if (productOptional.isPresent()) {
                Products productToUpdate = productOptional.get();

                BigDecimal previousCost = productToUpdate.getCost();
                BigDecimal previousQuantity = productToUpdate.getQuantity();

                BigDecimal totalSpend = costs.getTotalSpend();
                BigDecimal newTotal = costs.getTotal().subtract(previousCost.multiply(previousQuantity))
                        .add(productCost.multiply(productQuantity));

                costs.setTotal(newTotal);
                costs.setPercents(generate.calculatePercentage(newTotal, totalSpend));
                costs.setBalance(totalSpend.subtract(newTotal));

                productToUpdate.setQuantity(productQuantity);
                productToUpdate.setCost(productCost);
                productToUpdate.setProduct(product.getProduct());
            }
            chatsRepository.save(chats);
            messagingTemplate.convertAndSend(path + chats.getId(), costs);


        }
    }
}
