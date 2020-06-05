package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.model.BankTransfert;
import com.paymybuddy.paysystem.model.CreditCardOperation;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.transaction.TransactionService;
import com.paymybuddy.paysystem.web.exceptions.TransactionBankTransfertCanNotbeAddedException;
import com.paymybuddy.paysystem.web.exceptions.TransactionCanNotbeAddedException;
import com.paymybuddy.paysystem.web.exceptions.TransactionCreditCardCanNotbeAddedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransfertController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);
    @Autowired
    private TransactionService transactionService;

    /*--------------------------- POST : Creation d'un transfert entre amis----------------*/

    /**
     * Realise a transfert between to person. before tey must be buddy
     * @param transactionBuddy Informations about the transfert
     * @return Informations about the transfert
     * @throws TransactionCanNotbeAddedException Exception if error
     */
    @PostMapping(value="/transaction/buddy")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionBuddy transactionBuddy(@RequestBody TransactionBuddy transactionBuddy) throws TransactionCanNotbeAddedException {

        logger.info("TransactionBuddy start ");
        TransactionBuddy transactionBuddyResult = transactionService.payBuddy(transactionBuddy);


        if (transactionBuddyResult == null) {
            throw new TransactionCanNotbeAddedException(" Payment not possible for : " + transactionBuddy.getTransactionAmount());
        }
        logger.info("TransactionBuddy finish ");
        return transactionBuddyResult;
    }

    /*--------------------------- POST : versement par CB dans l'application ----------------*/

    /**
     * Credit card operation to provide my account
     * @param email my personal email
     * @param creditCardOperation creditcard operation info
     * @return account amount
     * @throws TransactionCreditCardCanNotbeAddedException if error
     */
    @PostMapping(value="/transaction/creditcard/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public double transactionCreditCard(@PathVariable String email,@RequestBody CreditCardOperation creditCardOperation) throws TransactionCreditCardCanNotbeAddedException {
        logger.info("transactionCreditCard start ");

        double amountAcountResult = 0;
        logger.info("transactionCreditCard start : " + email + " CB description" + creditCardOperation.getDescription() );
        amountAcountResult = transactionService.creditCardTransaction(email,creditCardOperation);
        if (amountAcountResult == -1) {
            throw new TransactionCreditCardCanNotbeAddedException(" Credit Card Operation not possible for : " + email + " amount "+ creditCardOperation.getAmount());
        }
        logger.info("transactionCreditCard start ");
        return amountAcountResult;
    }
    /*--------------------------- POST : Virement à la bank ----------------*/

    /**
     * bank transaction operation
     * @param email my personal email
     * @param bankTransfert bank transfert operation info
     * @return account amount
     * @throws TransactionBankTransfertCanNotbeAddedException if error like not enough money in account
     */
    @PostMapping(value="/transaction/bank/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public double transactionBankTransfert(@PathVariable String email,@RequestBody BankTransfert bankTransfert) throws TransactionBankTransfertCanNotbeAddedException {
        logger.info("transactionBankTransfert start ");
        double amountAcountResult = 0;
        logger.info("transactionBankTransfert start : " + email + " CB description" + bankTransfert.getDescription() );
        amountAcountResult = transactionService.bankTransaction(email,bankTransfert);
        if (amountAcountResult == -1) {
            throw new TransactionBankTransfertCanNotbeAddedException(" Bank Transfert Operation not possible for : " + email + " amount "+ bankTransfert.getAmount());
        }
        logger.info("transactionBankTransfert finish ");
        return amountAcountResult;
    }
}
