package com.chat.chatcalc.model.room;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class SendMessage {
    @NonNull()
    private String chatId;
    @NonNull()
    private String userId;
    @NonNull()
    private String content;
}
