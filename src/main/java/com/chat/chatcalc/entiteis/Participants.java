package com.chat.chatcalc.entiteis;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participants {

    private String userId;
    private String role;
    private LocalDateTime joinAt;
}
