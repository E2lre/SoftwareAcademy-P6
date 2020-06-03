package com.paymybuddy.paysystem.ut.person;

import com.paymybuddy.paysystem.config.JwtTokenProvider;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.SignIn;
import com.paymybuddy.paysystem.service.person.PersonService;
import com.paymybuddy.paysystem.service.util.UtilService;
import com.paymybuddy.paysystem.web.exceptions.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PersonServiceTest {
    private static final Logger logger = LogManager.getLogger(PersonServiceTest.class);
    @Autowired
    private PersonService personService;

    @MockBean
    private PersonDao personDao;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UtilService utilService;

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

    /*------------------------ SignUP ---------------------------------*/
    /**
     * PersonService
     * Test : create a new user
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void signup_createAnInexistingUSer_theJWTTokenIsReturn(){
        //GIVEN
        Mockito.when(personDao.existsByEmail(anyString())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(encryptPasswordConst);
        Mockito.when(personDao.save(any(Person.class))).thenReturn(personMock);
        //Mockito.doNothing().when(personDao).save(any(Person.class));
        Mockito.when(jwtTokenProvider.createToken(anyString(),any(List.class))).thenReturn(jwtTockenConst);
        //WHEN
        String result = personService.signup(personMock);
        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(jwtTockenConst);
        verify(passwordEncoder, Mockito.times(1)).encode(anyString());
        verify(personDao, Mockito.times(1)).save(any(Person.class));



    }

    /**
     * PersonService
     * Test : create a new user
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void signup_createAnExistingUSer_errorIsReturn() {
        //GIVEN
        Mockito.when(personDao.existsByEmail(anyString())).thenReturn(true);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(encryptPasswordConst);
        Mockito.when(personDao.save(any(Person.class))).thenReturn(personMock);
        //Mockito.doNothing().when(personDao).save(any(Person.class));
        Mockito.when(jwtTokenProvider.createToken(anyString(), any(List.class))).thenReturn(jwtTockenConst);
        //WHEN
        //THEN
        assertThrows(CustomException.class, () -> personService.signup(personMock));
    }
        /*------------------------ SignIN ---------------------------------*/
        /**
         * PersonService
         * Test : Check user
         */
        //TODO TEst SignIN
     //   @Test
        public void signin_anExistingUserTryToConnect_theJWTTokenIsReturn(){
            //GIVEN
  /*          Authentication authentication = mock(Authentication.class);
            Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
  */          //Mockito.when(personDao.existsByEmail(anyString())).thenReturn(false);
            SignIn signin = new SignIn(emailConst,passwordConst);
  //          Mockito.when(jwtTokenProvider.createToken(anyString(),any(List.class))).thenReturn(jwtTockenConst);
            //WHEN
            String result = personService.signin(signin);
            //THEN
            assertThat(result).isNotEmpty();
            assertThat(result).isEqualTo(jwtTockenConst);
            verify(passwordEncoder, Mockito.times(1)).encode(anyString());

 /*       assertThat(personServiceTest.getLastName()).isEqualTo(lastNameConst);
        assertThat(personServiceTest.getAddress()).isEqualTo(addressConst);
        assertThat(personServiceTest.getCity()).isEqualTo(cityConst);
        assertThat(personServiceTest.getZip()).isEqualTo(zipConst);
        assertThat(personServiceTest.getPhone()).isEqualTo(phoneConst);
        assertThat(personServiceTest.getEmail()).isEqualTo(emailConst);*/


        }

        /**
         * PersonService
         * Test : Check User
         */
        //TODO TEst SignIN
       // @Test
        public void signin_anIncorrectUserTryToConnect_errorIsReturn(){
            //GIVEN
            SignIn signin = new SignIn(emailConst,incorrectpasswordConst);
            Mockito.when(jwtTokenProvider.createToken(anyString(),any(List.class))).thenReturn(jwtTockenConst);
            //WHEN
            //THEN
            assertThrows(CustomException.class, () -> personService.signin(signin));



        }

    /*------------------------ addBuddy ---------------------------------*/
    /**
     * PersonService
     * Test : add a buddy
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addBuddy_addAnInexistingBuddy_buddyIsReturn(){
        //GIVEN
        MyBuddy myBuddy = new MyBuddy(emailConst,buddyEmailConst,"","");
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(personMock);

        Mockito.when(utilService.checkBuddy(anyString(),anyString())).thenReturn(false);
        Mockito.when(personDao.save(any(Person.class))).thenReturn(personMock);

        //WHEN
        MyBuddy buddyResult = personService.addBuddy(myBuddy);
        //THEN
        assertThat(buddyResult).isNotNull();
        assertThat(buddyResult.getBuddyFirstname()).isNotEmpty();
        assertThat(buddyResult.getBuddyLastname()).isNotEmpty();


    }

    /**
     * PersonService
     * Test : add a buddy
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addbuddy_addAnExistingBuddy_errorIsReturn() {
        //GIVEN
        MyBuddy myBuddy = new MyBuddy(emailConst,buddyEmailConst,"","");
        Mockito.when(personDao.findByEmail(anyString())).thenReturn(personMock);

        Mockito.when(utilService.checkBuddy(anyString(),anyString())).thenReturn(true);
        Mockito.when(personDao.save(any(Person.class))).thenReturn(personMock);

        //WHEN
        MyBuddy buddyResult = personService.addBuddy(myBuddy);
        //THEN
        assertThat(buddyResult).isNull();
    }

    /*------------------------ findAll ---------------------------------*/
    /**
     * PersonService
     * Test : find All person
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findAll_WhenDBISNotEmpty_PersonLisIsReturn(){
        //GIVEN
        List<Person> personList = new ArrayList<>();
        personList.add(personMock);
        Mockito.when(personDao.findAll()).thenReturn(personList);

        //WHEN
        List<Person> personResultList =  personService.findAll();
        //THEN
        assertThat(personResultList).isNotEmpty();



    }
    /*------------------------ findFriendByEmail ---------------------------------*/
    /**
     * PersonService
     * Test : find Friend By Email
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findFriendByEmail_giveAnExistingbuddyEmail_PersonIsReturn(){
        //GIVEN

        Mockito.when(personDao.findByEmail(anyString())).thenReturn(personMock);

        //WHEN
        List<Person> personResultList=  personService.findFriendByEmail(emailConst);
        //THEN
        assertThat(personResultList).isNotEmpty();



    }

    /**
     * PersonService
     * Test : find Friend By Email
     */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findFriendByEmail_giveAnInexistingbuddyEmail_errorIsReturn() {

        Mockito.when(personDao.findByEmail(anyString())).thenReturn(null);

        //WHEN
        List<Person> personResultList=  personService.findFriendByEmail(emailConst);
        //THEN
        assertThat(personResultList).isNull();

    }
}
