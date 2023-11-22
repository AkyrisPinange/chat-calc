package com.chat.chatcalc.model.user;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserName {


    @NotNull()
    private String newUserName;
    @NotNull(message = "userId cannot be null")
    private String userId;
}
