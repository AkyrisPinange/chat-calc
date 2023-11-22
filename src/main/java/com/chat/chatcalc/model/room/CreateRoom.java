package com.chat.chatcalc.model.room;

import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor

public class CreateRoom {
    @NotNull()
    private String title;
    @NotNull()
    private String userId;

}
