package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Person;
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

    /*---------------------------  Post -----------------------------*/


    @PostMapping(value="/Person")
    public Person savePerson(@RequestBody Person person) {
        // public Person addPerson(@Valid @RequestBody Person person) {

        //logger.info("POST/person=" + person);

        //Person personResult = personDao.save(person);

        //logger.info("POST /person : " + personResult);

        //return personResult;
        return personDao.save(person);
    }
}
