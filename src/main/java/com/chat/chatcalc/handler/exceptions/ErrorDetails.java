package com.chat.chatcalc.handler.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.util.MultiValueMap;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class ErrorDetails {

    private Integer status;
    private String message;

    private MultiValueMap<String, String> messages;

    public ErrorDetails(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
    public ErrorDetails(Integer status, MultiValueMap<String, String> messages) {
        this.status = status;
        this.messages = messages;
    }

}