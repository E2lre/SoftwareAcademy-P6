package com.paymybuddy.paysystem.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.paysystem.config.View;
import com.paymybuddy.paysystem.doa.AccountDao;
import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.SignIn;
import com.paymybuddy.paysystem.service.person.PersonService;
import com.paymybuddy.paysystem.web.exceptions.BuddyCanNotbeAddedException;
import com.paymybuddy.paysystem.web.exceptions.PersonCanNotbeAddedException;
import com.paymybuddy.paysystem.web.exceptions.SinginIncorrectEmailPasswordException;
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
    /*---------------------------  GET Find All -----------------------------*/
    /**
     * get all person in data base
     * @return list of person
     */
    @GetMapping(value = "Persons")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> ListPersons() {
        return personDao.findAll();
        //TODO Gérer les erreurs

    }
    /*---------------------------  GET Find By Id -----------------------------*/
    //TODO A supprimer si necessaire
    @GetMapping(value = "Person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person showPerson(@PathVariable long id) {
        return personDao.findById(id);
        //return null;
    }

    /*---------------------------  GET Find By Id -----------------------------*/
    //TODO A supprimer si necessaire
    @GetMapping(value = "PersonName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Person showPerson(@PathVariable String name) {
        return personDao.findByLastName(name);
        //return null;
    }

    /*---------------------------  GET Find friend By email -----------------------------*/

    @GetMapping(value = "PersonBuddy/{email}")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findFriendByEmail(@PathVariable String email) {
        //TODO GERER LES CAS D ERREUR
        return personService.findFriendByEmail(email);
        //return null;
    }
//TODO A supprimer
    @GetMapping(value = "Personpwd/{pwd}")
    public boolean checkPwd(@PathVariable String pwd) {
        logger.info("Personpwd");
        return personService.checkPwdPerson(pwd);
        //return null;
    }

    /*---------------------------  Get SignIn -----------------------------*/

    /**
     * API to sign in
     * @param signIn email and password
     * @return JWT tocken if ok
     * @throws SinginIncorrectEmailPasswordException exception if error
     */
    @GetMapping(value = "/signin")
    @ResponseStatus(HttpStatus.OK)
    public String checkPwd(@RequestBody SignIn signIn) throws SinginIncorrectEmailPasswordException {
        logger.info("Signin");
        String result = personService.signin(signIn);
        if ((result == null) || (result.isEmpty())){
            throw new SinginIncorrectEmailPasswordException(" Incorrect Email/Password for email " + signIn.getEmail());
        }

        return result;
        //return null;
    }
    /*---------------------------  Post CRUD-----------------------------*/
    //TODO A ssuprimer si necessaire
    //@JsonView(View.User.class)
    @PostMapping(value="/Person")
    public Person savePerson(@RequestBody Person person) {

        logger.info("Person");
        return personService.savePerson(person);
    }

    /*---------------------------  Post Signup-----------------------------*/
    //@JsonView(View.User.class)

    /**
     *  API to sign up
     * @param person info to create person and login
     * @return jwt token if ok
     */
    @PostMapping(value="/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody Person person) {
        // public Person addPerson(@Valid @RequestBody Person person) {

        //logger.info("POST/person=" + person);

        //Person personResult = personDao.save(person);

        //logger.info("POST /person : " + personResult);
//TODO Gestion des erreurs
        //return personResult;
        logger.info("signup");
        return personService.signup(person);
    }
    /*--------------------------- POST : Creation d'un user et de son compte associé----------------*/
    //@JsonView(View.User.class)
    //TODO a supprimer
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
/*---------------------------  Post addBuddy -----------------------------*/
/**
 *
 * @param myBuddy
 * @return
 */

    @PostMapping(value="/addBuddy")
    @ResponseStatus(HttpStatus.CREATED)
    public MyBuddy addBuddy(@RequestBody MyBuddy myBuddy) throws BuddyCanNotbeAddedException {
        logger.info("addBuddy start : " + myBuddy.getBuddyEmail());
        //TODO GERER LES CAS D ERREUR
        logger.info("addBuddy");

        MyBuddy buddyResult = personService.addBuddy(myBuddy);

        if (buddyResult == null) {
            throw new BuddyCanNotbeAddedException(" Buddy " + buddyResult.getBuddyEmail() +" can not be create");
        }
        return buddyResult;
    }
}

