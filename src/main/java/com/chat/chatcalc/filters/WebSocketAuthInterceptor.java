package com.chat.chatcalc.filters;

import com.chat.chatcalc.handler.exceptions.UserUnauthorizedException;
import com.chat.chatcalc.service.UserService;
import com.chat.chatcalc.service.auth.JwtService;
import lombok.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserService userService;

    public WebSocketAuthInterceptor(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            System.out.println("LINE 30 :->  " + accessor.toString());
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Obtenha o valor do cabeçalho "Authorization" da solicitação WebSocket
            List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
            System.out.println("LINE 34 :->  " + authorizationHeaders.toString());
            if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
                String authorizationHeader = authorizationHeaders.get(0);
                System.out.println("LINE 37 :->  " + authorizationHeader.toString());

                if (authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring(7);
                    System.out.println("LINE 41 :->  " + token.toString());

                    String userEmail = jwtService.extractUserName(token);
                    System.out.println("LINE 44 :->  " + userEmail.toString());

                    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
                    System.out.println("LINE 47 :->  " + userEmail.toString());
                    if (!jwtService.isTokenValid(token, userDetails)) {
                        System.out.println("LINE 49 :->  Token inválido ou sessão expirada");
                        throw new UserUnauthorizedException("Token inválido ou sessão expirada");
                    }
                } else {
                    System.out.println("LINE 53 :->  Token JWT mal formatado");
                    throw new UserUnauthorizedException("Token JWT mal formatado");
                }
            } else {
                System.out.println("LINE 57 :->  Token JWT ausente na solicitação");
                throw new UserUnauthorizedException("Token JWT ausente na solicitação");
            }
        }

        return message;
    }
}
