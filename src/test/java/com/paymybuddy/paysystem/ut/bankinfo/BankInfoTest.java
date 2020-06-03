package com.paymybuddy.paysystem.ut.bankinfo;

import com.paymybuddy.paysystem.dao.BankInfoDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.BankInfo;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.service.bankinfo.BankInfoService;
import com.paymybuddy.paysystem.service.transaction.TransactionService;
import com.paymybuddy.paysystem.ut.transaction.TransactionServiceTest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BankInfoTest {
    private static final Logger logger = LogManager.getLogger(TransactionServiceTest.class);
    @Autowired
    private BankInfoService bankInfoService;
    @MockBean
    private PersonDao personDao;
    @MockBean
    private BankInfoDao bankInfoDao;

    private Person myPerson;
    private BankInfo bankInfo;

    //constantes de test
    String firstNameConst = "Tatiana";
    String lastNameConst = "Romanova";
    String emailConst = "tatiana.romanova@bonsbaisersderussie.ru";
    String birthdateConst = "01/13/1693";

    String bankinfoDescription="Description bankinfo 1";
    String bankinfoInfo = "1234567980";
    int bankinfoType = 1;

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

            bankInfo = new BankInfo();
            bankInfo.setPerson(myPerson);
            bankInfo.setDescription(bankinfoDescription);
            bankInfo.setInfo(bankinfoInfo);
            bankInfo.setType(bankinfoType);



        } catch (ParseException e){
            logger.error(e.getMessage());
        }
    }
    /*------------------------ getBankInfo ---------------------------------*/

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getBankInfo_existingEmail_bankInfoIsDone() {
        //GIVEN
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(myPerson);
        List<BankInfo> bankInfoList = new ArrayList<>();
        bankInfoList.add(bankInfo);
        Mockito.when(bankInfoDao.findByPerson(any(Person.class))).thenReturn(bankInfoList);
        //WHEN
        List<BankInfo> bankinfoResult = bankInfoService.getBankInfo(emailConst);
        //THEN
        assertThat(bankinfoResult).isNotNull();
        assertThat(bankinfoResult).size().isEqualTo(1);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getBankInfo_inexistingEmail_errorIsreturn() {
        //GIVEN
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(null);
        List<BankInfo> bankInfoList = new ArrayList<>();
        bankInfoList.add(bankInfo);
        Mockito.when(bankInfoDao.findByPerson(any(Person.class))).thenReturn(bankInfoList);
        //WHEN
        List<BankInfo> bankinfoResult = bankInfoService.getBankInfo(emailConst);
        //THEN
        assertThat(bankinfoResult).isNull();
    }

    /*------------------------saveBankInfo ---------------------------------*/

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveBankInfo_existingEmail_bankInfoIsDone() {
        //GIVEN
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(myPerson);
        Mockito.when(bankInfoDao.save(any(BankInfo.class))).thenReturn(bankInfo);
        //WHEN
        BankInfo bankinfoResult = bankInfoService.saveBankInfo(bankInfo);
        //THEN
        assertThat(bankinfoResult).isNotNull();
        assertThat(bankinfoResult.getInfo()).isEqualTo(bankinfoInfo);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void saveBankInfo_inexistingEmail_errorIsreturn() {
        //GIVEN
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(null);
        Mockito.when(bankInfoDao.save(any(BankInfo.class))).thenReturn(bankInfo);
        //WHEN
        BankInfo bankinfoResult = bankInfoService.saveBankInfo(bankInfo);
        //THEN
        assertThat(bankinfoResult).isNull();
    }
}
