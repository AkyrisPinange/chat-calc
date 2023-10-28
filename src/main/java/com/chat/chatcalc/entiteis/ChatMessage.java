package com.chat.chatcalc.entiteis;

import com.chat.chatcalc.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatMessage {

    @Id
    private String id;
    private String chatId;
    private String userId;
    private String content;
    private String username;
    private Long timestamp;
    private MessageType type;
    private List<String> confirmations = new ArrayList<>();

    public ChatMessage(String content, MessageType messageType) {
        this.content = content;
        this.type = messageType;
    }

    public ChatMessage(String id, String chatId, String userId, String content, String username, Long timestamp, MessageType type) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
        this.type = type;
    }
}
