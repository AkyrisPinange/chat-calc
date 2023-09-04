package com.chat.chatcalc.model;

import com.chat.chatcalc.enums.MessageType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
public class ChatMessage {

    @Id
    private String id;
    private String chat_id;
    private String content;
    private String username;
    private String timestemp;
    private MessageType type;
}
