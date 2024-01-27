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
    private String name;
    private Long timestamp;
    private Costs costs;
    private MessageType type;
    private List<String> confirmations = new ArrayList<>();

    public ChatMessage(String content, MessageType messageType) {
        this.content = content;
        this.type = messageType;
    }

    public ChatMessage(String id, String chatId, String userId, String content, String name, Long timestamp, MessageType type) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
        this.name = name;
        this.timestamp = timestamp;
        this.type = type;
    }

}
