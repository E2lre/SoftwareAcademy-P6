package com.paymybuddy.paysystem.it;

import com.paymybuddy.paysystem.dao.BankInfoDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.BankInfo;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.TransactionBuddy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class BankInfoControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private BankInfoDao bankInfoDao;

    private Person myPerson;

    //constantes de test
    String emailConst = "james.bond@mi6.uk";
    String incorrectFirstnameConst = "Hubert";
    String incorrectLastnameConst = "Bonisseur de la Bath";
    String incorrectEmailConst = "hubert.bonisseurdelabath@oss117.fr";
    int type = 1;
    String info = "0123456789";
    String description = "Description Bank Info";

    /*---------------------------------------- getBankInfo -------------------------------*/
    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getBankInfo_existingEmail_bankInfoIsDone() throws Exception {
        //Given

        //WHEN THEN
        mockMvc.perform(get("/bankinfo/"+emailConst))
                 .andDo(print())
                 .andExpect(status().isOk());


    }

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getBankInfo_inexistingEmail_errorISReturn() throws Exception {
        //Given

        //WHEN THEN
        mockMvc.perform(get("/bankinfo/"+incorrectEmailConst))
                .andDo(print())
                .andExpect(status().isNotFound());


    }
    /*---------------------------------------- saveBankInfo -------------------------------*/
    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveBankInfo_existingEmail_bankInfoIsDone() throws Exception {
        //Given
        Person myPerson = personDao.findByEmail(emailConst);
        List<BankInfo> myBankInfoList1 = bankInfoDao.findByPerson(myPerson);

        //WHEN THEN
        mockMvc.perform(post("/bankinfo")
                .content(asJsonString(new BankInfo(myPerson,type,info,description)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        List<BankInfo> myBankInfoList2 = bankInfoDao.findByPerson(myPerson);

        assertThat(myBankInfoList2.size()).isEqualTo(myBankInfoList1.size() + 1);


    }

    @Test
    @WithMockUser(roles = "USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveBankInfo_inexistingEmail_errorISReturn() throws Exception {
        //Given

        //Given
        Person myPerson = new Person ();
        myPerson.setLastName(incorrectLastnameConst);
        myPerson.setFirstName(incorrectFirstnameConst);
        myPerson.setEmail(incorrectEmailConst);


        //WHEN THEN
        mockMvc.perform(post("/bankinfo")
                .content(asJsonString(new BankInfo(myPerson,type,info,description)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());



    }
}
