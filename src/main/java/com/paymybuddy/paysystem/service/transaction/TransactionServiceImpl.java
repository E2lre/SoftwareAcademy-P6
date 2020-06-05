package com.paymybuddy.paysystem.service.transaction;

import com.paymybuddy.paysystem.dao.*;
import com.paymybuddy.paysystem.model.*;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.person.PersonServiceImpl;
import com.paymybuddy.paysystem.service.util.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    private CreditCardOperationDao creditCardOperationDao;
    @Autowired
    private BankTransfertDao bankTransfertDao;
    @Autowired
    private BankInfoDao bankInfoDao;
    @Autowired
    private UtilService utilService;

    /**
     * Pay my buddy
     * @param transactionBuddy information transfert
     * @return information after transfert with fee
     */
    @Transactional
    public TransactionBuddy payBuddy(TransactionBuddy transactionBuddy){
        TransactionBuddy transactionBuddyResult = null;
        Person myPerson = null;
        Person buddyPerson = null;
        double amount = 0;
        boolean theyAreBuddy=false;
        double fee = 0;

        logger.info("addTransaction start. ");
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
                theyAreBuddy = utilService.checkBuddy(transactionBuddy.getMyEmail(),transactionBuddy.getBuddyEmail());

                if (theyAreBuddy) {
                    //Check my account level
                    Account myAccount = accountDao.findByPerson(myPerson) ;
                    if(myAccount.getBalance()>transactionBuddy.getTransactionAmount()) {
                        //Debit my acount

                        fee = transactionBuddy.getTransactionAmount() * feeTx;
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

                        //Credit application account for fee
                        Account systemeAccount = accountDao.findById(systemeAccountId);
                        amount =  systemeAccount.getBalance() + fee;
                        systemeAccount.setBalance(amount);
                        accountDao.save(systemeAccount);

                        transactionBuddyResult = transactionBuddy;
                        transactionBuddyResult.setFeeAmount(fee);
                    } else {
                        logger.error("Not enough money for " + transactionBuddy.getMyEmail() +" to transfert "+ transactionBuddy.getTransactionAmount() +" to "+transactionBuddy.getBuddyEmail());
                    }
                } else {
                    logger.error(transactionBuddy.getMyEmail() + "and " + transactionBuddy.getBuddyEmail() +"are not buddy");
                }
            } else {
                logger.error("nobody for this email " + transactionBuddy.getBuddyEmail());
            }
        } else {
            logger.error("nobody for this email " + transactionBuddy.getMyEmail());
        }
        logger.info("addTransaction finish");

    return transactionBuddyResult;
    }


    /**
     * Realise a credit card operation to creadit acount
     * @param email my email to credit my account
     * @param creditCardOperation information of creadit card with amount transaction
     * @return new account amount. -1 if problem
     */
    @Transactional
    public double creditCardTransaction(String email, CreditCardOperation creditCardOperation){
        logger.info("start creditCardTransaction with email "+email);
        boolean isOk = false;
        double amount = 0;
        Person myPerson = null;
        myPerson =  personDao.findByEmail(email);

        //Check if person exist
        if (myPerson != null) {
            //Valid authorisation  with the bank ==> Imagine, you are connected to the bank to get Authorisation
            //Check fields are not empty
            Date now = new Date();
            if ((creditCardOperation.getCreditCardNumber() !=0) &&
                (creditCardOperation.getCcv() !=0) &&
                (creditCardOperation.getExpirationDate().after(now)) &&
                (creditCardOperation.getAmount() != 0)) {

                //Credit amount of the person
                Account myAccount = accountDao.findByPerson(myPerson);
                amount = myAccount.getBalance() + creditCardOperation.getAmount();
                myAccount.setBalance(amount);
                accountDao.save(myAccount);

                creditCardOperation.setTransactionDate(now);
                creditCardOperationDao.save(creditCardOperation);
          } else {
                amount = -1;
                logger.error("incorrect credit card inf" + creditCardOperation.getCreditCardNumber() + "/"+creditCardOperation.getCcv()+"/"+creditCardOperation.getExpirationDate() );
            }

        } else {
            amount = -1;
            logger.error("nobody for this email " + email);
        }
        logger.info("creditCardTransaction finish correctly with email "+email);

        return amount;
    }

    /**
     * Realise a transfert of my account to the bank
     * @param email my email
     * @param bankTransfert information with bank info and amount
     * @return new account amount. -1 if problem
     */
    @Transactional
    public double bankTransaction(String email, BankTransfert bankTransfert){
        logger.debug("start bankTransaction with email "+email);
        boolean isOk = false;
        double amount = -1;
        Person myPerson = null;
        myPerson =  personDao.findByEmail(email);

        //Check if person exist
        if (myPerson != null) {
            //Valid authorisation  with the bank ==> Imagine, you are connected to the bank to get Authorisation
            //Check fields are not empty
            Date now = new Date();
            //Credit amount of the person
            Account myAccount = accountDao.findByPerson(myPerson);
            amount = myAccount.getBalance() - bankTransfert.getAmount();
            //There is enough money
            if (amount>=0) {
                myAccount.setBalance(amount);
                accountDao.save(myAccount);

                //Get bankinfo for id
                BankInfo bankInfoInput = bankTransfert.getBankinfo();
                if (bankInfoInput != null) {

                    BankInfo bankInfo = bankInfoDao.findById(bankInfoInput.getId());
                    bankTransfert.setBankinfo(bankInfo);
                    bankTransfert.setTransactionDate(now);

                    ///bank Transfert  on transaction table.
                    bankTransfertDao.save(bankTransfert);
                } else {
                    amount = -1;
                    logger.error("error on bankinfo for " + email + "and id bankinfo" + bankTransfert.getBankinfo());
                }
            }else {
                amount = -1;
                logger.error("error on bankinfo for " + email + " : not enough money : balance :" + myAccount.getBalance());
            }

        } else {
            amount = -1;
            logger.error("nobody for this email " + email);
        }
        logger.info("bankTransaction finish correctly with email "+email);

        return amount;

    }
}
