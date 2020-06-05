package com.paymybuddy.paysystem.it;


import com.paymybuddy.paysystem.dao.AccountDao;
import com.paymybuddy.paysystem.dao.BankInfoDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.*;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.person.PersonService;
import com.paymybuddy.paysystem.ut.transaction.TransactionServiceTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static com.paymybuddy.paysystem.ut.person.PersonControllerTest.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@EnableAutoConfiguration(exclude = {SecurityFilterAutoConfiguration.class, SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
public class TransactionControllerIT {
    private static final Logger logger = LogManager.getLogger(TransactionControllerIT.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private BankInfoDao bankInfoDao;
    @Autowired
    private PersonService personService;


    //constantes de test
    String emailConst = "james.bond@mi6.uk";
    String incorrectEmailConst = "hubert.bonisseurdelabath@oss117.fr";
    String buddyEmailConst = "vesper.lynd@casinoroyal.com";

    /*---------------------------------------- payBuddy-------------------------------*/
    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void payBuddy_existingPersonPayABuddy_transfertIsDone() throws Exception {
        //Given
        double transactionAmout = 10;
        double feeAmount=0;
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        int systemeAccountId = Integer.parseInt(bundle.getString("application.accountid"));
        double feeTx = Double.parseDouble(bundle.getString("application.fee.rate"));
        double resultFeeAmount=transactionAmout*feeTx;
        Person myPerson = personDao.findByEmail(emailConst);
        Account myAccount = accountDao.findByPerson(myPerson);
        double startBalance = myAccount.getBalance();
        MyBuddy myBuddy = new MyBuddy(emailConst,buddyEmailConst,"","") ; //create buddy
        personService.addBuddy(myBuddy);

        //WHEN THEN
        mockMvc.perform(post("/transaction/buddy")
                .content(asJsonString(new TransactionBuddy(emailConst,buddyEmailConst,"My Description",transactionAmout,feeAmount)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        myAccount = accountDao.findByPerson(myPerson);
        assertThat(myAccount.getBalance()).isEqualTo(startBalance-transactionAmout-resultFeeAmount);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void payBuddy_existingPersonPayASomeOneNotBuddy_errorIsReturn() throws Exception {
        //Given
        double transactionAmout = 10;
        double feeAmount=0;
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        int systemeAccountId = Integer.parseInt(bundle.getString("application.accountid"));
        double feeTx = Double.parseDouble(bundle.getString("application.fee.rate"));
        double resultFeeAmount=transactionAmout*feeTx;
        Person myPerson = personDao.findByEmail(emailConst);
        Account myAccount = accountDao.findByPerson(myPerson);
        double startBalance = myAccount.getBalance();

        //WHEN THEN
        mockMvc.perform(post("/transaction/buddy")
                .content(asJsonString(new TransactionBuddy(emailConst,buddyEmailConst,"My Description",transactionAmout,feeAmount)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

       }

    /*---------------------------------------- transactionCreditCard-------------------------------*/

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void transactionCreditCard_correctInformationCB_transfertIsDone() throws Exception {
        //Given
        CreditCardOperation creditCardOperation= new CreditCardOperation();
        creditCardOperation.setCreditCardNumber(1234567890);
        creditCardOperation.setCcv(123);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date expirationDate;
            expirationDate = simpleDateFormat.parse("01/01/2030");
            creditCardOperation.setExpirationDate(expirationDate);
        } catch (ParseException e){
            logger.error(e.getMessage());
        }
        creditCardOperation.setAmount(10);
        creditCardOperation.setDescription("Description CB Test");
        Person myPerson = personDao.findByEmail(emailConst);
        Account myAccount = accountDao.findByPerson(myPerson);
        double startBalance = myAccount.getBalance();

        //WHEN THEN
        mockMvc.perform(post("/transaction/creditcard/"+emailConst)
                .content(asJsonString(creditCardOperation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        myAccount = accountDao.findByPerson(myPerson);
        assertThat(myAccount.getBalance()).isEqualTo(startBalance+10);
    }

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void transactionCreditCard_incorrectInformationCB_errorIsReturn() throws Exception {
        //Given
        CreditCardOperation creditCardOperation= new CreditCardOperation();
        creditCardOperation.setCreditCardNumber(1234567890);
        creditCardOperation.setCcv(123);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date expirationDate;
            expirationDate = simpleDateFormat.parse("01/01/2030");
            creditCardOperation.setExpirationDate(expirationDate);
        } catch (ParseException e){
            logger.error(e.getMessage());
        }
        creditCardOperation.setAmount(10);
        creditCardOperation.setDescription("Description CB Test");

        //WHEN THEN
        mockMvc.perform(post("/transaction/creditcard/"+incorrectEmailConst)
                .content(asJsonString(creditCardOperation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

    }
    /*---------------------------------------- bank transfert Operation-------------------------------*/

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void bankTransaction_correctInformation_transfertIsDone() throws Exception {
        //Given
        BankTransfert bankTransfert= new BankTransfert();
        bankTransfert.setAmount(10);
        bankTransfert.setTransfertOrder(123465789);
        bankTransfert.setDescription("Virement bancaire");
        BankInfo bankInfo = new BankInfo();
        bankInfo.setId(2);
        bankTransfert.setBankinfo(bankInfo);


        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date expirationDate;
            expirationDate = simpleDateFormat.parse("01/01/2030");
            bankTransfert.setTransactionDate(expirationDate);
        } catch (ParseException e){
            logger.error(e.getMessage());
        }
        Person myPerson = personDao.findByEmail(emailConst);
        Account myAccount = accountDao.findByPerson(myPerson);
        double startBalance = myAccount.getBalance();


        //WHEN THEN
        mockMvc.perform(post("/transaction/bank/"+emailConst)
                .content(asJsonString(bankTransfert))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        myAccount = accountDao.findByPerson(myPerson);
        assertThat(myAccount.getBalance()).isEqualTo(startBalance-10);

    }

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void bankTransaction_incorrectInformation_errorIsReturn() throws Exception {
        //Given
        BankTransfert bankTransfert= new BankTransfert();
        bankTransfert.setAmount(10);
        bankTransfert.setTransfertOrder(123465789);
        bankTransfert.setDescription("Virement bancaire");
        BankInfo bankInfo = new BankInfo();
        bankInfo.setId(2);
        bankTransfert.setBankinfo(bankInfo);


        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date expirationDate;
            expirationDate = simpleDateFormat.parse("01/01/2030");
            bankTransfert.setTransactionDate(expirationDate);
        } catch (ParseException e){
            logger.error(e.getMessage());
        }

        //WHEN THEN
        mockMvc.perform(post("/transaction/bank/"+incorrectEmailConst)
                .content(asJsonString(bankTransfert))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

    }
}
