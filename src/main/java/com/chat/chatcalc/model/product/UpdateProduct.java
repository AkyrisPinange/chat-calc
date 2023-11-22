package com.chat.chatcalc.model.product;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor

public class UpdateProduct {
    @NotNull()
    private String productId;
    @NotNull()
    private BigDecimal cost;
    @NotNull()
    private BigDecimal quantity;
    @NotNull()
    private String product;

}
