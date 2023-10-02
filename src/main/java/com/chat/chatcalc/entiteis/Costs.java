package com.chat.chatcalc.entiteis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Costs {

    @Id
    private String id;
    private String chatId;
    private BigDecimal cost;
    private BigDecimal total;
    private BigDecimal quantity;
    private String product;

}
