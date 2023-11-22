package com.chat.chatcalc.model.room;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class DeleteChat {
    @NotNull()
    private String chatId;
    @NotNull()
    private String userId;
}
