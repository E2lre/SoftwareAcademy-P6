package com.paymybuddy.paysystem.service.transaction;

import com.paymybuddy.paysystem.model.BankTransfert;
import com.paymybuddy.paysystem.model.CreditCardOperation;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;

public interface TransactionService {
    public TransactionBuddy payBuddy(TransactionBuddy transactionBuddy);
    public double creditCardTransaction(String email, CreditCardOperation creditCardOperation);
    public double bankTransaction(String email, BankTransfert bankTransfert);
    }
