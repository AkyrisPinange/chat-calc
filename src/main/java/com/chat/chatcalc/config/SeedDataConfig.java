package com.chat.chatcalc.config;


import com.chat.chatcalc.enums.Role;
import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.reporsitory.UserRepository;
import com.chat.chatcalc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        
      if (userRepository.count() == 2) {

        User admin = User
                      .builder()
                      .id(UUID.randomUUID().toString())
                      .name("admin")
                      .email("admin@admin.com")
                      .password(passwordEncoder.encode("password"))
                      .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                      .build();

        userService.save(admin);
        log.debug("created ADMIN user - {}", admin);
      }
    }

}
