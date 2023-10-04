package com.chat.chatcalc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CostData {

    private String chatId;
    private BigDecimal cost;
    private BigDecimal quantity;
    private String product;
}