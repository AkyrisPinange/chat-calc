package com.chat.chatcalc.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SuccessResponse<T> implements Serializable {

    private String codigo;

    private String mensagem;

    private T objetoRetorno;

    public SuccessResponse(String codigo, String mensagem, T objetoRetorno) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.objetoRetorno = objetoRetorno;
    }

}
