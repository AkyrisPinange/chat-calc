package com.chat.chatcalc.controllers;

import com.chat.chatcalc.model.login.AuthenticationResponse;
import com.chat.chatcalc.model.login.SignInRequest;
import com.chat.chatcalc.service.auth.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public AuthenticationResponse signup(@RequestBody SignInRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("signin")
    public AuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}