package com.br.microservice.produto.config.exception;

import lombok.Data;

@Data
public class ExceptionDetails {

    private int status;
    private String message;


}
