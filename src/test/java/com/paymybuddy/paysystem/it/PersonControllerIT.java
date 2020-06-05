package com.paymybuddy.paysystem.it;


import com.paymybuddy.paysystem.model.Person;
//import com.paymybuddy.paysystem.model.questions.SignIn;
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

@AutoConfigureTestDatabase
public class PersonControllerIT {

    private TestRestTemplate template = new TestRestTemplate();
    private String username="user";
    private String password="password";

    @Autowired
    private MockMvc mockMvc;

    //constantes de test
    String existEmail = "vesper.lynd@casinoroyal.com";
    String existPasswordConst = "abc";
    String existIncorrectPasswordConst = "IncorrectPWD";

    /*---------------------------------------- Signin-------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveEmailandCorrectPassword_connectionIsAgree() throws Exception {
        //Given
        Person signin = new Person();
        signin.setEmail(existEmail);
        signin.setPassword(existPasswordConst);

        //WHEN //THEN return token
        mockMvc.perform(get("/signin")
                .content(asJsonString(signin))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void Signin_giveEmailandIncorrectPassword_connectionIsRefused() throws Exception {
        //Given
        Person signin = new Person();
        signin.setEmail(existEmail);
        signin.setPassword(existIncorrectPasswordConst);
        //WHEN //THEN return token
        mockMvc.perform(get("/signin")
                .content(asJsonString(signin))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
