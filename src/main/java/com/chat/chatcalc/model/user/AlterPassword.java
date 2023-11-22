package com.chat.chatcalc.model.user;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlterPassword {
   @NotNull()
   private String userId;
   @NotNull()
   private String currentPassword;
   @NotNull()
   private String newPassword;
}
