package com.chat.chatcalc.service.auth;


import com.chat.chatcalc.dto.AuthenticationResponse;
import com.chat.chatcalc.dto.SignInRequest;
import com.chat.chatcalc.dto.SignUpRequest;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.handler.exceptions.UserPasswordException;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.UserService;
import com.chat.chatcalc.utils.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public AuthenticationResponse signup(SignUpRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new NotFoundException("Invalid email already in use");
        }
        
        validate.validateEmail(request.getEmail());

        var user = User
                .builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt)
                .name(user.getName())
                .userId(user.getId())
                .build();
    }


    public AuthenticationResponse signin(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new UserPasswordException("Password or e-mail incorrect");
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt)
                .name(user.getName())
                .userId(user.getId())
                .build();
    }

}
