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
     * get all person in data base
     * @return list of person
     */
 /*   @GetMapping(value = "personsOLD")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> listPersons()  throws PersonListException {
        //List<Person> personList = new ArrayList<>();
        List<Person> personList = personService.findAll();
        if ((personList == null) || (personList.isEmpty())){
            throw new PersonListException("Impossible to return person List ");
        }

        return personList;


    }*/
    @GetMapping(value = "persons")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> listPersons2()  throws PersonListException {

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
        return personDTOList;


    }
    /*---------------------------  GET Find friend By email -----------------------------*/

    @GetMapping(value = "buddy/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findFriendByEmail(@PathVariable String email) throws BuddyListException{

        List<Person> buddyList = personService.findFriendByEmail(email);
        if ((buddyList == null) || (buddyList.isEmpty())){
            throw new BuddyListException("Impossible to return buddy List pour email " + email);
        }
        return buddyList;

    }

    /*---------------------------  Get SignIn -----------------------------*/

    /**
     * API to sign in
     * @param signIn email and password
     * @return JWT tocken if ok
     * @throws SinginIncorrectEmailPasswordException exception if error
     */
  /*  @GetMapping(value = "/signinOLD")
    @ResponseStatus(HttpStatus.OK)
    public String checkPwdOLD(@RequestBody SignIn signIn) throws SinginIncorrectEmailPasswordException {
        logger.info("Signin");
        String result = personService.signinOLD(signIn);
        if ((result == null) || (result.isEmpty())){
            throw new SinginIncorrectEmailPasswordException(" Incorrect Email/Password for email " + signIn.getEmail());
        }

        return result;

    }*/
    @GetMapping(value = "/signin")
    @ResponseStatus(HttpStatus.OK)
    public String checkPwd(@RequestBody Person person) throws SinginIncorrectEmailPasswordException {
        logger.info("Signin");
        String result = personService.signin(person);
        if ((result == null) || (result.isEmpty())){
            throw new SinginIncorrectEmailPasswordException(" Incorrect Email/Password for email " + person.getEmail());
        }

        return result;

    }
    /*---------------------------  Post CRUD-----------------------------*/
/*    //TODO A ssuprimer si necessaire
    //@JsonView(View.User.class)
    @PostMapping(value="/Person")
    public Person savePerson(@RequestBody Person person) {

        logger.info("Person");
        return personService.savePerson(person);
    }*/

    /*---------------------------  Post Signup-----------------------------*/
    //@JsonView(View.User.class)

    /**
     *  API to sign up
     * @param person info to create person and login
     * @return jwt token if ok
     */
    @PostMapping(value="/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody Person person) throws SignupException {

        logger.info("signup start : " );
        String Result = personService.signup(person);


        if (Result == null) {
            throw new SignupException(" Impossible to Signup email " + person.getEmail());
        }
        return Result;
    }
    /*--------------------------- POST : Creation d'un user et de son compte associé----------------*/
    //@JsonView(View.User.class)
/*    //TODO a supprimer
    @PostMapping(value="/CreateLogin")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createLogin(@RequestBody Person person) throws PersonCanNotbeAddedException {

        logger.info("createLogin start : " + person);
        Person personResult = personService.savePerson(person);


        if (personResult == null) {
            throw new PersonCanNotbeAddedException(" Email " + person.getEmail() +" already exist for " + person.getFirstName() + " " + person.getLastName() +". Person not created");
        }

        return personResult;
        }*/
/*---------------------------  Post addBuddy -----------------------------*/
/**
 *
 * @param myBuddy
 * @return
 */

    @PostMapping(value="/buddy")
    @ResponseStatus(HttpStatus.CREATED)
    public MyBuddy addBuddy(@RequestBody MyBuddy myBuddy) throws BuddyCanNotbeAddedException {
        logger.info("addBuddy start : " + myBuddy.getBuddyEmail());

        logger.info("buddy Controler");

        MyBuddy buddyResult = personService.addBuddy(myBuddy);

        if (buddyResult == null) {
            throw new BuddyCanNotbeAddedException(" Buddy " + myBuddy.getBuddyEmail() +" can not be create");
        }
        return buddyResult;
    }
}

