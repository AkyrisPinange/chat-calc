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

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Obtenha o valor do cabeçalho "Authorization" da solicitação WebSocket
            List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");

            if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
                String authorizationHeader = authorizationHeaders.get(0);

                if (authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring(7);
                    String userEmail = jwtService.extractUserName(token);
                    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

                    if (!jwtService.isTokenValid(token, userDetails)) {
                        throw new UserUnauthorizedException("Token inválido ou sessão expirada");
                    }
                } else {
                    throw new UserUnauthorizedException("Token JWT mal formatado");
                }
            } else {
                throw new UserUnauthorizedException("Token JWT ausente na solicitação");
            }
        }

        return message;
    }
}
