package com.paymybuddy.paysystem.ut;

import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonDao personDao;

    /*---------------------------------------- GET -------------------------------*/
    @Test
    @WithMockUser(roles="USER")
    public void PersonController_getExistingPerson_thePersonAskIsSended() throws Exception {
        List<Person> personList = new ArrayList<>();

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //LocalDate birthdate = LocalDate.parse("01/01/1990", formatter);

        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateAsString = "25/12/2010";
        Date birthdate = sourceFormat.parse(dateAsString);

        Person person = new Person(1,"james","Bond", birthdate,"james.bond@li6.com","bac");
        personList.add(person);
        //GIVEN : Give an exiting Person
        Mockito.when(personDao.findAll()).thenReturn(personList);
        //WHEN //THEN return the station
        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
