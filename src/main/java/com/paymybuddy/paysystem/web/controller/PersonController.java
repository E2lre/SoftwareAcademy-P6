package com.paymybuddy.paysystem.web.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
// com.paymybuddy.paysystem.model.questions.SignIn;
import com.paymybuddy.paysystem.model.questions.PersonDTO;
import com.paymybuddy.paysystem.service.person.PersonService;
import com.paymybuddy.paysystem.web.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonService personService;
    /*---------------------------  GET Find All -----------------------------*/

    /**
     * Get all person in data base
     * @return list of person with their buddys
     * @throws PersonListException Error if noboby in data base or systeme error
     */
    @GetMapping(value = "persons")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> listPersons()  throws PersonListException {
        logger.info("listPersons start");
        List<Person> personList = personService.findAll();
        if ((personList == null) || (personList.isEmpty())) {
            throw new PersonListException("Impossible to return person List ");
        }

        //Return correct format
        ModelMapper modelMapper = new ModelMapper();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person ePerson : personList) {
            PersonDTO personDTO = modelMapper.map(ePerson, PersonDTO.class);
            personDTOList.add(personDTO);
        }
        logger.info("findFriendByEmail finish");
        return personDTOList;
    }
    /*---------------------------  GET Find friend By email -----------------------------*/

    /**
     * Find all the friend of a person By the email of the person
     * @param email Email of the person to get their buddys
     * @return List of buddys
     * @throws BuddyListException Error if buddy list is empty or system error
     */
    @GetMapping(value = "buddy/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findFriendByEmail(@PathVariable String email) throws BuddyListException{
        logger.info("findFriendByEmail start");

        List<Person> buddyList = personService.findFriendByEmail(email);
        if ((buddyList == null) || (buddyList.isEmpty())){
            throw new BuddyListException("Impossible to return buddy List pour email " + email);
        }
        logger.info("findFriendByEmail finish");
        return buddyList;
    }

    /*---------------------------  Get SignIn -----------------------------*/

    /**
     * Signin for an existant person (user)
     * @param person Only email and password must be provide
     * @return JWT tocken
     * @throws SinginIncorrectEmailPasswordException exception if error
     */
    @GetMapping(value = "/signin")
    @ResponseStatus(HttpStatus.OK)
    public String checkPwd(@RequestBody Person person) throws SinginIncorrectEmailPasswordException {
        logger.info("checkPwd start");
        String result = personService.signin(person);
        if ((result == null) || (result.isEmpty())){
            throw new SinginIncorrectEmailPasswordException(" Incorrect Email/Password for email " + person.getEmail());
        }
        logger.info("checkPwd finish");
        return result;
    }


    /*---------------------------  Post Signup-----------------------------*/
    /**
     * Signup for a new person (user)
     * @param person All the information to create a new user
     * @return JWT tocken
     * @throws SignupException Exception if error
     */
    @PostMapping(value="/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody Person person) throws SignupException {

        logger.info("signup start");
        String Result = personService.signup(person);


        if (Result == null) {
            throw new SignupException(" Impossible to Signup email " + person.getEmail());
        }
        logger.info("checkPwd finish");
        return Result;
    }

/*---------------------------  Post addBuddy -----------------------------*/

    /**
     * Create a buddy relation between two persons
     * @param myBuddy All the information to create a buddy relation
     * @return All the information about the buddy relation
     * @throws BuddyCanNotbeAddedException Exception if error
     */
    @PostMapping(value="/buddy")
    @ResponseStatus(HttpStatus.CREATED)
    public MyBuddy addBuddy(@RequestBody MyBuddy myBuddy) throws BuddyCanNotbeAddedException {
        logger.info("addBuddy start : " + myBuddy.getBuddyEmail());

        MyBuddy buddyResult = personService.addBuddy(myBuddy);

        if (buddyResult == null) {
            throw new BuddyCanNotbeAddedException(" Buddy " + myBuddy.getBuddyEmail() +" can not be create");
        }
        logger.info("addBuddy finish ");
        return buddyResult;
    }
}

