package com.chat.chatcalc.controllers;

import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.model.user.AlterPassword;
import com.chat.chatcalc.model.SuccessResponse;
import com.chat.chatcalc.model.user.UpdateUserName;
import com.chat.chatcalc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/")
public class UserController {

    private final UserService userService;
    @PostMapping("updatePassword") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @Valid @RequestBody AlterPassword alterPassword
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Suceeso", userService.alterPassword(alterPassword)));

    }

    @PostMapping("updateUsername") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @Valid @RequestBody UpdateUserName updateUser
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Suceeso", userService.updateUserName(updateUser)));

    }

    @GetMapping("getUserById") // endpoint Api
    public ResponseEntity<SuccessResponse<User>> getUserById(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "Suceeso", userService.findUserById(userId)));

    }
}
