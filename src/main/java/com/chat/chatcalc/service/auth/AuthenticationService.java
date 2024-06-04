package com.chat.chatcalc.service.auth;


import com.chat.chatcalc.model.login.AuthenticationResponse;
import com.chat.chatcalc.model.login.SignInRequest;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.UserService;
import com.chat.chatcalc.utils.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Validate validate;

    public AuthenticationResponse signup(SignInRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new NotFoundException("Invalid email already in use");
        }
        validate.validateEmail(request.getEmail());

        var user = User
                .builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .urlImg(request.getUrlImg())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        user = userService.save(user);
        return returnAuthentication(user);
    }

    public AuthenticationResponse signin(SignInRequest request) {

        var user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user != null) {
           return returnAuthentication(user);
        } else {
            return signup(request);
        }
    }

    AuthenticationResponse returnAuthentication(User user) {
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt)
                .name(user.getName())
                .userId(user.getId())
                .urlImg(user.getUrlImg())
                .build();
    }
}
