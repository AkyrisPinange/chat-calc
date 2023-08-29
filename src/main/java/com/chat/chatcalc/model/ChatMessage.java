package com.chat.chatcalc.model;

import com.chat.chatcalc.enums.MessageType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;
}
