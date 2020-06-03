package com.paymybuddy.paysystem.ut;

import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Person;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UtilServiceTest {
    private static final Logger logger = LogManager.getLogger(UtilServiceTest.class);
    @Autowired
    private UtilService utilService;
    @MockBean
    private PersonDao personDao;

    private Person personMock;

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
            personMock = new Person();
            personMock.setFirstName(firstNameConst);
            personMock.setLastName(lastNameConst);

            personMock.setBirthdate(birthdate);
            personMock.setEmail(emailConst);
            personMock.setPassword(passwordConst);
            List<Person> personList = new ArrayList<>();
            personList.add(personMock);
            personMock.setBuddy(personList);

        } catch (ParseException e){
            logger.error(e.getMessage());
        }
    }
    /*------------------------ CheckBuddy ---------------------------------*/

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void checkBuddy_giveAnExistingEmailBuddy_trueIsReturn(){
        //GIVEN
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(personMock);

        Mockito.when(personDao.existsByEmailAndBuddy(anyString(),any(Person.class))).thenReturn(true);

        //WHEN
        Boolean result = utilService.checkBuddy(emailConst,buddyEmailConst);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void checkBuddy_giveAnInexistingEmailBuddy_falseIsReturn(){
        //GIVEN
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(personMock);

        Mockito.when(personDao.existsByEmailAndBuddy(anyString(),any(Person.class))).thenReturn(false);

        //WHEN
        Boolean result = utilService.checkBuddy(emailConst,buddyEmailConst);
        //THEN
        assertThat(result).isFalse();
    }
}
