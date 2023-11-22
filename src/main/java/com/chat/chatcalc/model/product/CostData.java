package com.chat.chatcalc.model.product;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CostData {
    @NotNull()
    private String chatId;
    @NotNull()
    private BigDecimal cost;
    @NotNull()
    private BigDecimal totalSpend;
    @NotNull()
    private BigDecimal quantity;
    @NotNull()
    private String product;
    @NotNull()
    private String userId;


}
