package com.paymybuddy.paysystem.it;


import com.paymybuddy.paysystem.model.questions.SignIn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static com.paymybuddy.paysystem.ut.person.PersonControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@EnableAutoConfiguration(exclude = {SecurityFilterAutoConfiguration.class, SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase
public class PersonControllerIT {

    private TestRestTemplate template = new TestRestTemplate();
    private String username="user";
    private String password="password";

    @Autowired
    private MockMvc mockMvc;

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

  /*  @Before
    public void beforeTest() throws MalformedURLException {
        template = new TestRestTemplate(username, password);
    }*/
    /*---------------------------------------- GET -------------------------------*/
   // @Test
   // @WithMockUser(roles="USER")
    public void PersonController_getExistingPerson_thePersonAskIsSended() throws Exception {

        //GIVEN : Give an exiting Person
        //template = new TestRestTemplate(username, password);
        //WHEN //THEN return the station
        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    /*---------------------------------------- Signin-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveEmailandCorrectPassword_connectionIsAgree() throws Exception {
        String token = "abcdef99";


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

        //WHEN //THEN return token
        mockMvc.perform(get("/signin")
                .content(asJsonString(new SignIn(existEmail,existIncorrectPasswordConst)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());


    }

}
