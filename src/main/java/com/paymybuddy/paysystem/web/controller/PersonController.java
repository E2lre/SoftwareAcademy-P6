package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.doa.AccountDao;
import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.service.person.PersonService;
import com.paymybuddy.paysystem.web.exceptions.PersonCanNotbeAddedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonService personService;
    /*---------------------------  Find All -----------------------------*/
    @GetMapping(value = "Persons")
    public List<Person> ListPersons() {
        return personDao.findAll();
        //return null;
    }

    @GetMapping(value = "Person/{id}")
    public Person showPerson(@PathVariable int id) {
        return personDao.findById(id);
        //return null;
    }
    @GetMapping(value = "PersonName/{name}")
    public Person showPerson(@PathVariable String name) {
        return personDao.findByLastName(name);
        //return null;
    }

    @GetMapping(value = "Personpwd/{pwd}")
    public boolean checkPwd(@PathVariable String pwd) {
        logger.info("Personpwd");
        return personService.checkPwdPerson(pwd);
        //return null;
    }

    /*---------------------------  Post CRUD-----------------------------*/
    @PostMapping(value="/Person")
    public Person savePerson(@RequestBody Person person) {
        // public Person addPerson(@Valid @RequestBody Person person) {

        //logger.info("POST/person=" + person);

        //Person personResult = personDao.save(person);

        //logger.info("POST /person : " + personResult);

        //return personResult;
        logger.info("Person");
        return personService.savePerson(person);
    }
    /*--------------------------- POST : Creation d'un user et de son compte associé----------------*/
    @PostMapping(value="/CreateLogin")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createLogin(@RequestBody Person person) throws PersonCanNotbeAddedException {

        logger.info("createLogin start : " + person);
        Person personResult = personService.savePerson(person);


        if (personResult == null) {
            throw new PersonCanNotbeAddedException(" Email " + person.getEmail() +" already exist for " + person.getFirstName() + " " + person.getLastName() +". Person not created");
        }

        return personResult;
        }

}
