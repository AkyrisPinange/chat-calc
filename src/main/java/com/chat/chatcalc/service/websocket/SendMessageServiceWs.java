package com.chat.chatcalc.service.websocket;

import com.chat.chatcalc.entiteis.Chats;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.enums.MessageType;
import com.chat.chatcalc.model.room.SendMessage;
import com.chat.chatcalc.service.WebSocketService;
import com.chat.chatcalc.utils.DataRetrievalUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendMessageServiceWs {

    private final WebSocketService webSocketService;
    private final DataRetrievalUtil dataRetrievalUtil;

    public void sendMessage(SendMessage sendMessage) {
        User user = dataRetrievalUtil.getUser(sendMessage.getUserId(), sendMessage.getChatId());
        Chats chat = dataRetrievalUtil.getChat(sendMessage.getChatId());

        webSocketService.sendMessage( user, chat, sendMessage.getContent(),  MessageType.SEND);
    }
}
