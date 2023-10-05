package com.chat.chatcalc.handler;


import com.chat.chatcalc.handler.exceptions.ErrorDetails;
import com.chat.chatcalc.handler.exceptions.NotFoundException;
import com.chat.chatcalc.handler.exceptions.UserPasswordException;
import com.chat.chatcalc.handler.exceptions.UserUnauthorizedException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundException(NotFoundException ex) {
        ErrorDetails errorModel = new ErrorDetails(404 , ex.getMessage());

        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorDetails> signatureException(SignatureException ex) {
        ErrorDetails errorModel = new ErrorDetails(403 , "Assinatura JWT inválida");

        return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN); // Use o código de status apropriado
    }
    @ExceptionHandler(UserPasswordException.class)
    public ResponseEntity<ErrorDetails> userPasswordException(UserPasswordException ex) {
        ErrorDetails errorModel = new ErrorDetails(0, ex.getMessage());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex) {
        ErrorDetails errorModel = new ErrorDetails(500, ex.getMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        MultiValueMap<String, String> messages = new LinkedMultiValueMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String campo = error.getField();
            String message = error.getDefaultMessage();
            messages.add(campo, message);
        });
        ErrorDetails errorModel = new ErrorDetails(0, messages);
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);

    }
}