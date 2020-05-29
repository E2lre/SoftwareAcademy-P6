package com.paymybuddy.paysystem.web.exceptions;

import org.springframework.http.HttpStatus;

//TODO vérifier si il faut la déplacer (utilisé par security)
public  class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}