package com.chat.chatcalc.model.room;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinRoom {
    @NonNull()
    private String chatId;
    @NonNull()
    private String userId;
}
