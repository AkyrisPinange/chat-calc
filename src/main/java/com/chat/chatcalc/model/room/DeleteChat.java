package com.chat.chatcalc.model.room;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class DeleteChat {
    @NonNull()
    private String chatId;
    @NonNull()
    private String userId;
}
