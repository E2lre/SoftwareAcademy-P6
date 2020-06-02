package com.paymybuddy.paysystem.ut;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.SignIn;
import com.paymybuddy.paysystem.service.person.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PersonControllerTest {
    private static final Logger logger = LogManager.getLogger(PersonControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    private Person personMock;
    //constantes de test
    String firstNameConst = "Tatiana";
    String lastNameConst = "Romanova";
    String emailConst = "tatiana.romanova@bonsbaisersderussie.ru";
    String birthdateConst = "01/13/1693";
    Date birthdate;
    String passwordConst = "SPECTRE";
    String encryptPasswordConst = "$2a$12$scj6PvgZYRLahntmwOmm/.PnXJjHYK2SpsgsWb6fFbZBr5nWpbmJ6";
    String incorrectpasswordConst = "MI6";
    String jwtTockenConst = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXkuZGF5QGRhbmdldXJlbWVudHZvdHJlLmZyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9DTElFTlQifV0sImlhdCI6MTU5MDc2MTM1NCwiZXhwIjoxNTkwNzY0OTU0fQ.Wflx8fsnoUiUzruGKBLrS2PFL4DpKyoaZsi5bcQkakY";

    String buddyFirstNameConst = "Pussy";
    String buddyLastNameConst = "Galore";
    String buddyEmailConst = "pussy.galore@Goldfinger.or";

    String existEmail = "vesper.lynd@casinoroyal.com";
    String existPasswordConst = "abc";
    String existIncorrectPasswordConst = "IncorrectPWD";

    @BeforeEach
    public void setUpEach() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            birthdate = simpleDateFormat.parse(birthdateConst);
            personMock = new Person();
            personMock.setFirstName(firstNameConst);
            personMock.setLastName(lastNameConst);

            personMock.setBirthdate(birthdate);
            personMock.setEmail(emailConst);
            personMock.setPassword(passwordConst);
            Person buddy = new Person(99,buddyFirstNameConst,buddyLastNameConst,birthdate,buddyEmailConst,passwordConst);
            List<Person> buddyList = new ArrayList<>();
            buddyList.add(buddy);
            personMock.setBuddy(buddyList);

        } catch (ParseException e){
            logger.error(e.getMessage());
        }
    }
    /*---------------------------------------- GET Persons-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findAll_AskAllPersonInDataBase_thePersonLisIsSended() throws Exception {
        List<Person> personList = new ArrayList<>();

        //GIVEN : Give an exiting Person
        personList.add(personMock);
        Mockito.when(personService.findAll()).thenReturn(personList);
        //WHEN //THEN return the station
        mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findAll_AskAllPersonInEmptyDataBase_errorIsSended() throws Exception {

        //GIVEN : Give an exiting Person
        Mockito.when(personService.findAll()).thenReturn(null);
        //WHEN //THEN return the station
        mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    /*---------------------------------------- Find friend By email-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findFriendByEmail_giveEmail_theBuddyLisIsSended() throws Exception {
        List<Person> personList = new ArrayList<>();

        //GIVEN : Give an exiting Person
        personList.add(personMock);

        Mockito.when(personService.findFriendByEmail(anyString())).thenReturn(personList);
        //WHEN //THEN return list off buddy

        mockMvc.perform(get("/buddy/"+emailConst))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void findFriendByEmail_giveEmailWithNoBuddy_errorIsSended() throws Exception {
        List<Person> personList = new ArrayList<>();

        //GIVEN : Give an exiting Person
        personList.add(personMock);

        Mockito.when(personService.findFriendByEmail(anyString())).thenReturn(null);
        //WHEN //THEN return list off buddy

        mockMvc.perform(get("/buddy/"+emailConst))
                .andExpect(status().isNotFound());
    }

    /*---------------------------------------- Signin-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveEmailandCorrectPassword_connectionIsAgree() throws Exception {
        String token = "abcdef99";

        Mockito.when(personService.signin(any(SignIn.class))).thenReturn(token);
        //WHEN //THEN return token
         mockMvc.perform(get("/signin")
                .content(asJsonString(new SignIn(existEmail,existPasswordConst)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveEmailandIncorrectPassword_connectionIsRefused() throws Exception {
        String token = "abcdef99";

        Mockito.when(personService.signin(any(SignIn.class))).thenReturn(null);
        //WHEN //THEN return token
        mockMvc.perform(get("/signin")
                .content(asJsonString(new SignIn(existEmail,existIncorrectPasswordConst)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());


    }

    /*---------------------------------------- Signup-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signup_giveAnInexistingEmailandPassword_connectionIsAgree() throws Exception {
        String token = "abcdef99";

        //GIVEN : Create a login
        Mockito.when(personService.signup(any(Person.class))).thenReturn(token);
        //WHEN //THEN return token

        mockMvc.perform(post("/signup")
                .content(asJsonString(new Person(99,buddyFirstNameConst,buddyLastNameConst,birthdate,buddyEmailConst,passwordConst)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signup_giveAnExistingEmailandPassword_errorIsReturn() throws Exception {
        String token = "abcdef99";
        List<Person> personList = new ArrayList<>();

        //GIVEN : Create a login

        Mockito.when(personService.signup(any(Person.class))).thenReturn(null);
        //WHEN //THEN return token

        mockMvc.perform(post("/signup")
                .content(asJsonString(new Person(99,buddyFirstNameConst,buddyLastNameConst,birthdate,buddyEmailConst,passwordConst)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

    }


    /*---------------------------------------- addBuddy-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addBuddy_giveAnExistingpersonNotBuddy_buddyIsReturn() throws Exception {
        MyBuddy myBuddy = new MyBuddy(emailConst,buddyEmailConst,buddyFirstNameConst,buddyLastNameConst);

        //GIVEN : Create a login
        Mockito.when(personService.addBuddy(any(MyBuddy.class))).thenReturn(myBuddy);
        //WHEN //THEN return token

        mockMvc.perform(post("/buddy")
                .content(asJsonString(new MyBuddy(emailConst,buddyEmailConst,"","")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.buddyFirstname").value(buddyFirstNameConst))
                .andExpect(jsonPath("$.buddyLastname").value(buddyLastNameConst));

    }

    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addBuddy_giveAnInexistingpersonNotBuddy_errorIsReturn() throws Exception {
        MyBuddy myBuddy = new MyBuddy(emailConst,buddyEmailConst,buddyFirstNameConst,buddyLastNameConst);

        //GIVEN : Create a login
        Mockito.when(personService.addBuddy(any(MyBuddy.class))).thenReturn(null);
        //WHEN //THEN return token

        mockMvc.perform(post("/buddy")
                .content(asJsonString(new MyBuddy(emailConst,buddyEmailConst,"","")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());


    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
