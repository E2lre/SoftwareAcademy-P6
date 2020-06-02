package com.paymybuddy.paysystem.web.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonListException extends Exception {
    private static final Logger logger = LogManager.getLogger(PersonListException.class);

    public PersonListException(String s) {
        super(s);
        logger.error(s);
    }
}
