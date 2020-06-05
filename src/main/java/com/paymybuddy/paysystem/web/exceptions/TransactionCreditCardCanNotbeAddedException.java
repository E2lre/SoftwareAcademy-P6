package com.paymybuddy.paysystem.web.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TransactionCreditCardCanNotbeAddedException extends Exception {
    private static final Logger logger = LogManager.getLogger(TransactionCreditCardCanNotbeAddedException.class);

    public TransactionCreditCardCanNotbeAddedException(String s) {
        super(s);
        logger.error(s);
    }
}