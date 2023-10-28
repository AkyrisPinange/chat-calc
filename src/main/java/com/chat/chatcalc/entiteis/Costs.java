package com.chat.chatcalc.entiteis;

import com.chat.chatcalc.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Costs {

    @Id
    private String chatId;
    private BigDecimal total;
    private BigDecimal totalSpend;
    private String percents;
    private List<Products> products = new ArrayList<>();
    private MessageType type;

}
