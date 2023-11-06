package com.chat.chatcalc.service.websocket.cost;


import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.Costs;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.ChangeSpend;
import com.chat.chatcalc.reporsitory.ChatsRepository;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.WebSocketService;
import com.chat.chatcalc.utils.Generate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ChangeSpendService {
    private final UserRepository userRepository;
    private final ChatsRepository chatsRepository;
    private final WebSocketService webSocketService;
    private final Generate generate;

    public ChangeSpendService(UserRepository userRepository, ChatsRepository chatsRepository, WebSocketService webSocketService, Generate generate) {
        this.userRepository = userRepository;
        this.chatsRepository = chatsRepository;
        this.webSocketService = webSocketService;
        this.generate = generate;
    }

    public void changeSpend(ChangeSpend changeSpend) {

        Chats chat = chatsRepository.findById(changeSpend.getChatId()).orElse(null);

        if (chat == null) {
            webSocketService.errorMessageByChatId(changeSpend.getChatId(), "Chat not found");
            return;
        }
        User user = userRepository.findById(changeSpend.getUserId()).orElse(null);
        if (user == null) {
            webSocketService.errorMessageByChatId(changeSpend.getChatId(), "User not found");
            return;
        }

        Costs cost = chat.getCosts();

        BigDecimal newPercent = generate.calculatePercentage(cost.getTotal(), changeSpend.getTotalSpend());
        cost.setPercents(newPercent);
        cost.setBalance(changeSpend.getTotalSpend().subtract(cost.getTotal()));
        cost.setTotalSpend(changeSpend.getTotalSpend());

        chatsRepository.save(chat);

        String message = user.getName() + " alterou o valor a gastar para: " + changeSpend.getTotalSpend();
        webSocketService.sendMessage(user, chat, message, MessageType.PRICE);
    }
}