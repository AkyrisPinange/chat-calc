package com.chat.chatcalc.model.product;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class DeleteCost {
    @NotNull()
    String chatId;
    @NotNull()
    String productId;

}
