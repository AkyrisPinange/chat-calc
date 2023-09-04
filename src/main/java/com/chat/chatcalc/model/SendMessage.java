package com.chat.chatcalc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMessage {
    private String chat_id;
    private String user_id;
    private String content;
}
