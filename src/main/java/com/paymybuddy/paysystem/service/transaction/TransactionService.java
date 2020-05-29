package com.paymybuddy.paysystem.service.transaction;

import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;

public interface TransactionService {
    public TransactionBuddy addTransaction (TransactionBuddy transactionBuddy);
}
