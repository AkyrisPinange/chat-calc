package com.chat.chatcalc.model.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ChangeSpend {
    @NonNull()
    private String chatId;
    @NonNull()
    private String userId;
    @NonNull()
    private BigDecimal totalSpend;
}
