package com.chat.chatcalc.model.room;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor

public class CreateRoom {
    @NonNull()
    private String title;
    @NonNull()
    private String userId;

}
