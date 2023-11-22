package com.chat.chatcalc.model.user;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlterPassword {
   @NonNull()
   private String userId;
   @NonNull()
   private String currentPassword;
   @NonNull()
   private String newPassword;
}
