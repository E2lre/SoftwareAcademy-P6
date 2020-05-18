package com.paymybuddy.paysystem.it;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

  /*  @Before
    public void beforeTest() throws MalformedURLException {
        template = new TestRestTemplate(username, password);
    }*/
    /*---------------------------------------- GET -------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    public void PersonController_getExistingPerson_thePersonAskIsSended() throws Exception {

        //GIVEN : Give an exiting Person
        //template = new TestRestTemplate(username, password);
        //WHEN //THEN return the station
        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
