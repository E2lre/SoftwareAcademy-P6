package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.doa.TransactionDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    private TransactionDao transactionDao;

    /*---------------------------  Get -----------------------------*/
    @GetMapping(value = "Transactions")
    public List<Transaction> ListTransactions() {
        return transactionDao.findAll();
        //return null;
    }

    /*---------------------------  Post -----------------------------*/


    @PostMapping(value="/Transaction")
    public Transaction savePerson(@RequestBody Transaction transaction) {

        logger.info("POST/Transaction =" + transaction);


        return  transactionDao.save(transaction);
    }
}
