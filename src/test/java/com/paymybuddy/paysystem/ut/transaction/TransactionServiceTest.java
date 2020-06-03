package com.paymybuddy.paysystem.ut.transaction;

import com.paymybuddy.paysystem.dao.AccountDao;
import com.paymybuddy.paysystem.dao.CreditDao;
import com.paymybuddy.paysystem.dao.PaymentDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.Credit;
import com.paymybuddy.paysystem.model.Payment;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.transaction.TransactionService;
import com.paymybuddy.paysystem.service.util.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TransactionServiceTest {
    private static final Logger logger = LogManager.getLogger(TransactionServiceTest.class);
    @Autowired
    private TransactionService transactionService;
    @MockBean
    private PersonDao personDao;
    @MockBean
    private AccountDao accountDao;
    @MockBean
    private PaymentDao paymentDao;
    @MockBean
    private CreditDao creditDao;
    @MockBean
    private UtilService utilService;

    private Person myPerson;
    private Account myAccount;
    private Person buddyPerson;
    private Account buddyAccount;

    //constantes de test
    String firstNameConst = "Tatiana";
    String lastNameConst = "Romanova";
    String emailConst = "tatiana.romanova@bonsbaisersderussie.ru";
    String birthdateConst = "01/13/1693";
    String passwordConst = "SPECTRE";
    String encryptPasswordConst = "$2a$12$scj6PvgZYRLahntmwOmm/.PnXJjHYK2SpsgsWb6fFbZBr5nWpbmJ6";
    String incorrectpasswordConst = "MI6";
    String jwtTockenConst = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXkuZGF5QGRhbmdldXJlbWVudHZvdHJlLmZyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9DTElFTlQifV0sImlhdCI6MTU5MDc2MTM1NCwiZXhwIjoxNTkwNzY0OTU0fQ.Wflx8fsnoUiUzruGKBLrS2PFL4DpKyoaZsi5bcQkakY";

    String buddyFirstNameConst = "Pussy";
    String buddyLastNameConst = "Galore";
    String buddyEmailConst = "pussy.galore@Goldfinger.or";
    @BeforeEach
    public void setUpEach() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date birthdate = simpleDateFormat.parse(birthdateConst);
            myPerson = new Person();
            myPerson.setFirstName(firstNameConst);
            myPerson.setLastName(lastNameConst);

            myPerson.setBirthdate(birthdate);
            myPerson.setEmail(emailConst);
            //myPerson.setPassword(passwordConst);

            buddyPerson = new Person();
            buddyPerson.setFirstName(buddyFirstNameConst);
            buddyPerson.setLastName(buddyLastNameConst);
            buddyPerson.setEmail(buddyEmailConst);
            List<Person> personList = new ArrayList<>();
            personList.add(buddyPerson);
            myPerson.setBuddy(personList);

            myAccount = new Account();
            myAccount.setBalance(1000);
            buddyAccount = new Account();
            buddyAccount.setBalance(1000);




        } catch (ParseException e){
            logger.error(e.getMessage());
        }
    }

    /*------------------------ payBuddy ---------------------------------*/

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void payBuddy_existingPersonPayABuddy_transfertIsDone(){
        //GIVEN

        Mockito.when(personDao.findByEmail(emailConst)).thenReturn(myPerson);
        Mockito.when(personDao.findByEmail(buddyEmailConst)).thenReturn(buddyPerson);
        Mockito.when(utilService.checkBuddy(anyString(),anyString())).thenReturn(true);
        Mockito.when(accountDao.findByPerson(myPerson)).thenReturn(myAccount);
        Mockito.when(accountDao.save(myAccount)).thenReturn(myAccount);
        Mockito.when(accountDao.findByPerson(buddyPerson)).thenReturn(buddyAccount);
        Mockito.when(accountDao.save(buddyAccount)).thenReturn(buddyAccount);
        Payment myPayment = new Payment();
        Mockito.when(paymentDao.save(myPayment)).thenReturn(myPayment);
        Credit buddyCredit = new Credit();
        Mockito.when(creditDao.save(buddyCredit)).thenReturn(buddyCredit);
        Account systemeAccount = new Account();
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        int systemeAccountId = Integer.parseInt(bundle.getString("application.accountid"));
        Account systemAccount = new Account();
        systemAccount.setBalance(1000);
        Mockito.when(accountDao.findById(systemeAccountId)).thenReturn(systemAccount);

        double transactionAmout = 10;
        double feeAmount=0;
        double feeTx = Double.parseDouble(bundle.getString("application.fee.rate"));
        double resultFeeAmount=transactionAmout*feeTx;

        TransactionBuddy transactionBuddy = new TransactionBuddy(emailConst,buddyEmailConst,"My description",transactionAmout,feeAmount);

        //WHEN
        TransactionBuddy result = transactionService.payBuddy(transactionBuddy);
        //THEN
        assertThat(result).isNotNull();
        assertThat(result.getFeeAmount()).isEqualTo(resultFeeAmount);
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void payBuddy_existingPersonWithNoMoneyPayBuddy_errorIsDone(){
        //GIVEN
        myAccount.setBalance(0);
        Mockito.when(personDao.findByEmail(emailConst)).thenReturn(myPerson);
        Mockito.when(personDao.findByEmail(buddyEmailConst)).thenReturn(buddyPerson);
        Mockito.when(utilService.checkBuddy(anyString(),anyString())).thenReturn(true);
        Mockito.when(accountDao.findByPerson(myPerson)).thenReturn(myAccount);
        Mockito.when(accountDao.save(myAccount)).thenReturn(myAccount);
        Mockito.when(accountDao.findByPerson(buddyPerson)).thenReturn(buddyAccount);
        Mockito.when(accountDao.save(buddyAccount)).thenReturn(buddyAccount);
        Payment myPayment = new Payment();
        Mockito.when(paymentDao.save(myPayment)).thenReturn(myPayment);
        Credit buddyCredit = new Credit();
        Mockito.when(creditDao.save(buddyCredit)).thenReturn(buddyCredit);
        Account systemeAccount = new Account();
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        int systemeAccountId = Integer.parseInt(bundle.getString("application.accountid"));
        Account systemAccount = new Account();
        systemAccount.setBalance(1000);
        Mockito.when(accountDao.findById(systemeAccountId)).thenReturn(systemAccount);

        double transactionAmout = 10;
        double feeAmount=0;
        double feeTx = Double.parseDouble(bundle.getString("application.fee.rate"));
        double resultFeeAmount=transactionAmout*feeTx;

        TransactionBuddy transactionBuddy = new TransactionBuddy(emailConst,buddyEmailConst,"My description",transactionAmout,feeAmount);

        //WHEN
        TransactionBuddy result = transactionService.payBuddy(transactionBuddy);
        //THEN
        assertThat(result).isNull();
    }
}
