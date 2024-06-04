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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    @PutMapping("updatePassword") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @Valid @RequestBody AlterPassword alterPassword
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "User updated", userService.alterPassword(alterPassword)));

    }

    @PutMapping("updateUsername") // endpoint Api
    public ResponseEntity<SuccessResponse<String>> updatePassword(
            @Valid @RequestBody UpdateUserName updateUser
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "User updated", userService.updateUserName(updateUser)));

    }

    @GetMapping("userById") // endpoint Api
    public ResponseEntity<SuccessResponse<User>> getUserById(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(new SuccessResponse<>("200", "User list", userService.findUserById(userId)));

    }
}
