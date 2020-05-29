package com.paymybuddy.paysystem.service.transaction;

import com.paymybuddy.paysystem.doa.AccountDao;
import com.paymybuddy.paysystem.doa.CreditDao;
import com.paymybuddy.paysystem.doa.PaymentDao;
import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.*;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.person.PersonServiceImpl;
import com.paymybuddy.paysystem.service.util.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class TransactionServiceImpl implements TransactionService{

    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    @Autowired
    private PersonDao personDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private CreditDao creditDao;
    @Autowired
    private UtilService utilService;

    @Transactional
    public TransactionBuddy addTransaction (TransactionBuddy transactionBuddy){
        TransactionBuddy transactionBuddyResult = null;
        Person myPerson = null;
        Person buddyPerson = null;
        double amount = 0;
        boolean theyAreBuddy=false;
        double fee = 0;

//TODO Check person buddy sont buddy
        logger.debug("addTransaction start. info : " + transactionBuddy);
        //Get parameters values
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        double feeTx = Double.parseDouble(bundle.getString("application.fee.rate"));
        int systemeAccountId = Integer.parseInt(bundle.getString("application.accountid"));


        //Get my person
        myPerson =  personDao.findByEmail(transactionBuddy.getMyEmail());

        if (myPerson != null) {
            //Get buddy Person
            buddyPerson =  personDao.findByEmail(transactionBuddy.getBuddyEmail());
            if (buddyPerson != null) {
                //Are they friend
 //TODO THEY ARE FREIND Faire en jointure
/*                int index;
                for(index=0;index<myPerson.getBuddy().size();index++){
                    if (myPerson.getBuddy().get(index).getId() == buddyPerson.getId()) {
                        theyAreBuddy = true;
                    }
                }*/
                theyAreBuddy = utilService.checkBuddy(transactionBuddy.getMyEmail(),transactionBuddy.getBuddyEmail());
               //TODO  theyAreBuddy = personDao.existsByEmailAndBuddy(myPerson.getEmail(),buddyPerson);
                if (theyAreBuddy) {
                    //Check my account level
                    Account myAccount = accountDao.findByPerson(myPerson) ;
                    if(myAccount.getBalance()>transactionBuddy.getTransactionAmount()) {
                        //Debit my acount

                        fee = transactionBuddy.getTransactionAmount() * feeTx; //TODO mettre en parametre
                        amount =  myAccount.getBalance() - transactionBuddy.getTransactionAmount()- fee;
                        myAccount.setBalance(amount);
                        accountDao.save(myAccount);

                        //Credit buddy acount
                        Account buddyAccount = accountDao.findByPerson(buddyPerson);
                        amount =  buddyAccount.getBalance() + transactionBuddy.getTransactionAmount();
                        buddyAccount.setBalance(amount);
                        accountDao.save(buddyAccount);

                        //Payment my transaction
                        Payment payment = new Payment();
                        payment.setBuddyPayment(buddyPerson);
                        payment.setFeeAmount(fee);
                        payment.setAmount(transactionBuddy.getTransactionAmount());
                        payment.setDescription(transactionBuddy.getDescription());
                        Date now = new Date();
                        payment.setTransactionDate(now);
                        paymentDao.save(payment);


                        //Credit buddy transaction
                        Credit credit = new Credit();
                        credit.setBuddyCredit(buddyPerson);
                        credit.setDescription(transactionBuddy.getDescription());
                        credit.setTransactionDate(now);
                        credit.setAmount(transactionBuddy.getTransactionAmount());
                        creditDao.save(credit);
                        //TODO aliment le compte des fee

                        //Credit application account
                        Account systemeAccount = accountDao.findById(systemeAccountId);
                        amount =  systemeAccount.getBalance() + fee;
                        systemeAccount.setBalance(amount);
                        accountDao.save(systemeAccount);

                        transactionBuddyResult = transactionBuddy;
                        transactionBuddyResult.setFeeAmount(fee);
                    }
                }
            }
        }

    return transactionBuddyResult;
    }

}
