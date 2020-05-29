package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.transaction.TransactionService;
import com.paymybuddy.paysystem.web.exceptions.PersonCanNotbeAddedException;
import com.paymybuddy.paysystem.web.exceptions.TransactionCanNotbeAddedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransfertController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);
    @Autowired
    private TransactionService transactionService;
    /*--------------------------- POST : Creation d'un transfert entre amis----------------*/

    @PostMapping(value="/TransactionBuddy")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionBuddy transactionBuddy(@RequestBody TransactionBuddy transactionBuddy) throws TransactionCanNotbeAddedException {

        logger.info("TransactionBuddy start : " + transactionBuddy);
        TransactionBuddy transactionBuddyResult = transactionService.addTransaction(transactionBuddy);


        if (transactionBuddyResult == null) {
            throw new TransactionCanNotbeAddedException(" Payment not possible for : " + transactionBuddy.getTransactionAmount());
        }

        return transactionBuddyResult;
    }
}
