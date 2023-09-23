package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.model.SendMessage;
import com.chat.chatcalc.utils.DataRetrievalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    private final WebSocktService webSocktService;
    private final DataRetrievalUtil dataRetrievalUtil;

    @Autowired
    public SendMessageService(DataRetrievalUtil dataRetrievalUtil, WebSocktService webSocktService) {
        this.webSocktService = webSocktService;
        this.dataRetrievalUtil = dataRetrievalUtil;
    }

    public void sendMessage(SendMessage sendMessage) {
        User user = dataRetrievalUtil.getUser(sendMessage.getUserId(), sendMessage.getChatId());
        Chats chat = dataRetrievalUtil.getChat(sendMessage.getChatId());

        webSocktService.sendChatMessage(chat, user, sendMessage.getContent());
    }
}
