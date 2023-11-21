package com.chat.chatcalc.controllers;

import com.chat.chatcalc.model.user.AlterPassword;
import com.chat.chatcalc.model.SuccessResponse;
import com.chat.chatcalc.model.user.UpdateUserName;
import com.chat.chatcalc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/")
public class UserController {

    private final UserService userService;
    @PostMapping("updatePassword") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @RequestBody AlterPassword alterPassword
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Suceeso", userService.alterPassword(alterPassword)));

    }

    @PostMapping("updateUsername") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @RequestBody UpdateUserName updateUser
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Suceeso", userService.updateUserName(updateUser)));

    }
}
