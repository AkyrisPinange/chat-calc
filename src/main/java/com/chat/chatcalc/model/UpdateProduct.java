package com.chat.chatcalc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProduct {

    private String productId;
    private String cost;
    private String quantity;
    private String product;

}
