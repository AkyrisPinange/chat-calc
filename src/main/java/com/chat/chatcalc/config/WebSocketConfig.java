package com.chat.chatcalc.config;


import com.chat.chatcalc.filters.WebSocketAuthInterceptor;
import com.chat.chatcalc.service.UserService;
import com.chat.chatcalc.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public WebSocketConfig(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Configure o interceptor WebSocket para autenticação
        registration.interceptors(new WebSocketAuthInterceptor(jwtService, userService));
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Habilita o suporte a SockJS para permitir que navegadores mais antigos se conectem
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefixo para mensagens que serão enviadas de volta aos clientes
        registry.setApplicationDestinationPrefixes("/app");
        // Prefixo para mensagens destinadas ao servidor
        registry.enableSimpleBroker("/topic");
    }

}
