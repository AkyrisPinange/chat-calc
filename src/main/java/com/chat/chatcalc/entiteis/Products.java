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
public class Products {

    @Id
    private String id;
    private BigDecimal cost;
    private BigDecimal quantity;
    private String product;
    private String name;
    private String userId;
    private Long timestamp;
}
