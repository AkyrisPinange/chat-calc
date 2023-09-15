package com.chat.chatcalc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoom {
    @NotBlank(message = "O campo 'title' é obrigatório")
    private String title;
    @NotBlank(message = "O campo 'userId' é obrigatório")
    private String userId;

}
