package com.chat.chatcalc.model.room;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class SendMessage {
    @NotNull()
    private String chatId;
    @NotNull()
    private String userId;
    @NotNull()
    private String content;
}
