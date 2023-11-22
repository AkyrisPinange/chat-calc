package com.chat.chatcalc.model.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CostData {
    @NonNull()
    private String chatId;
    @NonNull()
    private BigDecimal cost;
    @NonNull()
    private BigDecimal totalSpend;
    @NonNull()
    private BigDecimal quantity;
    @NonNull()
    private String product;
    @NonNull()
    private String userId;


}
