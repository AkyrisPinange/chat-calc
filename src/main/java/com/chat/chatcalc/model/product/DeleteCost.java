package com.chat.chatcalc.model.product;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class DeleteCost {
    @NonNull()
    String chatId;
    @NonNull()
    String productId;

}
