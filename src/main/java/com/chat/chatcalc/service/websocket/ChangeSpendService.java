package com.chat.chatcalc.service.websocket;


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

    public void changeSpend(ChangeSpend changeSpend){
          User user = userRepository.findById(changeSpend.getUserId()).orElse(null);
          Chats chat = chatsRepository.findById(changeSpend.getChatId()).orElse(null);

          Costs cost = chat.getCosts().isEmpty() ? null : chat.getCosts().get(chat.getCosts().size() - 1);

          String newPercent = generate.calculatePercentage(cost.getTotal(), changeSpend.getTotalSpend());
          cost.setPercents(newPercent);

          cost.setTotalSpend(changeSpend.getTotalSpend());

          chatsRepository.save(chat);

        webSocketService.sendMessage( user, chat, user.getName() + " alterou o valor à gastar para: " + changeSpend.getTotalSpend(),  MessageType.PRICE);



      }
}
