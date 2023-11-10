package com.chat.chatcalc.utils;

import com.chat.chatcalc.handler.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validate {

    public void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new NotFoundException("Email is not valid");
        }
    }
}
