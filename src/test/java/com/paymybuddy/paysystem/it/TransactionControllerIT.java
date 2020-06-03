package com.paymybuddy.paysystem.it;


import com.paymybuddy.paysystem.dao.AccountDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import com.paymybuddy.paysystem.service.person.PersonService;
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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PersonService personService;


    //constantes de test
    String firstNameConst = "Tatiana";
    String lastNameConst = "Romanova";
    String emailConst = "james.bond@mi6.uk";
    String birthdateConst = "01/13/1693";
    String passwordConst = "SPECTRE";
    String encryptPasswordConst = "$2a$12$scj6PvgZYRLahntmwOmm/.PnXJjHYK2SpsgsWb6fFbZBr5nWpbmJ6";
    String incorrectpasswordConst = "MI6";
    String jwtTockenConst = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXkuZGF5QGRhbmdldXJlbWVudHZvdHJlLmZyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9DTElFTlQifV0sImlhdCI6MTU5MDc2MTM1NCwiZXhwIjoxNTkwNzY0OTU0fQ.Wflx8fsnoUiUzruGKBLrS2PFL4DpKyoaZsi5bcQkakY";

    String buddyFirstNameConst = "Pussy";
    String buddyLastNameConst = "Galore";
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
    public void payBuddy_existingPersonPayASomeOneNotBuddy_transfertIsDone() throws Exception {
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

       // myAccount = accountDao.findByPerson(myPerson);
       // assertThat(myAccount.getBalance()).isEqualTo(startBalance-resultFeeAmount);


    }
}
