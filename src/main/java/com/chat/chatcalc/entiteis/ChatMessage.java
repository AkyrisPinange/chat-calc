package com.chat.chatcalc.entiteis;

import com.chat.chatcalc.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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
    private String content;
    private String username;
    private String timestamp;
    private MessageType type;

    public ChatMessage(String content, MessageType messageType) {
        this.content = content;
        this.type = messageType;
    }
}
