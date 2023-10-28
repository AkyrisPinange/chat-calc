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
public class ChangeSpend {

    private String chatId;
    private String userId;
    private BigDecimal totalSpend;
}
