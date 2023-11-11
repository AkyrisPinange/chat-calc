package com.chat.chatcalc.filters;

import com.chat.chatcalc.handler.exceptions.UserUnauthorizedException;
import com.chat.chatcalc.service.UserService;
import com.chat.chatcalc.service.auth.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");

            if (StringUtils.isNotEmpty(authHeader) && StringUtils.startsWith(authHeader, "Bearer ")) {
                String jwt = authHeader.substring(7);
                log.debug("JWT - {}", jwt);

                String userEmail = jwtService.extractUserName(jwt);

                if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        log.debug("User - {}", userDetails);

                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        context.setAuthentication(authToken);
                        SecurityContextHolder.setContext(context);

                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        throw new UserUnauthorizedException("Token invalid or session expiration");
                    }
                }
            }
        } catch (SignatureException e) {
            handleException(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (ExpiredJwtException e) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Token invalid or session expiration");
        }

        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.getWriter().write(message);
    }
}
