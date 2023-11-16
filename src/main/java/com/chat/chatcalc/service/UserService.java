package com.chat.chatcalc.service;

import com.chat.chatcalc.entiteis.User;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.model.AlterPassword;
import com.chat.chatcalc.model.UpdateUserName;
import com.chat.chatcalc.reporsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }

        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public String alterPassword(AlterPassword alterPassword) {
        User user = findUserById(alterPassword.getUserId());

        if (isCurrentPasswordCorrect(alterPassword.getCurrentPassword(), user.getPassword())) {
            updatePassword(user, alterPassword.getNewPassword());
            return "Senha Alterada com sucesso";
        } else {
            return "Senha informada não está correta";
        }
    }

    private boolean isCurrentPasswordCorrect(String currentPassword, String storedPassword) {
        return passwordEncoder.matches(currentPassword, storedPassword);
    }

    private void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public String updateUserName(UpdateUserName updateUser) {
        User user = findUserById(updateUser.getUserId());
        user.setName(updateUser.getNewUserName());

        userRepository.save(user);

        return "User Name alterado com sucesso";
    }

    private User findUserById(String userId) {
       return  userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

}
