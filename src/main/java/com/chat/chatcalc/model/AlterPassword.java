package com.chat.chatcalc.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlterPassword {

   private String userId;
   private String currentPassword;
   private String newPassword;
}