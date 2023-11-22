package com.chat.chatcalc.model.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor

public class UpdateProduct {
    @NonNull()
    private String productId;
    @NonNull()
    private BigDecimal cost;
    @NonNull()
    private BigDecimal quantity;
    @NonNull()
    private String product;

}
