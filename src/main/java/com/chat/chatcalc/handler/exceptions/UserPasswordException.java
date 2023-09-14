package com.chat.chatcalc.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserPasswordException extends RuntimeException{
    public UserPasswordException(String message){
        super(message);
    }   
}
