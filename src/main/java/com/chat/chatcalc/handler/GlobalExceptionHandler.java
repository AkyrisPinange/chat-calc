package com.chat.chatcalc.handler;


import com.chat.chatcalc.handler.exceptions.ErrorDetails;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.handler.exceptions.UserPasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.chat.chatcalc.enums.Errors.CHAT_NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundException(NotFoundException ex) {
        ErrorDetails errorModel = new ErrorDetails(200 , ex.getMessage());

        return new ResponseEntity<ErrorDetails>(errorModel, HttpStatus.OK);
    }

    @ExceptionHandler(UserPasswordException.class)
    public ResponseEntity<ErrorDetails> userPasswordException(UserPasswordException ex) {
        ErrorDetails errorModel = new ErrorDetails(0, ex.getMessage());

        return new ResponseEntity<ErrorDetails>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex) {
        ErrorDetails errorModel = new ErrorDetails(500, ex.getMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        MultiValueMap<String, String> messages = new LinkedMultiValueMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {

            String campo = ((FieldError) error).getField();
            String message = ((FieldError) error).getDefaultMessage();
            messages.add(campo, message);
        });
        ErrorDetails errorModel = new ErrorDetails(0, messages);
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);

    }
}