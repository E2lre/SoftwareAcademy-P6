package com.paymybuddy.paysystem.service.person;

import com.paymybuddy.paysystem.config.JwtTokenProvider;
import com.paymybuddy.paysystem.dao.AccountDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.Role;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.SignIn;
import com.paymybuddy.paysystem.service.util.UtilService;
import com.paymybuddy.paysystem.web.exceptions.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    @Autowired
    private PersonDao personDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UtilService utilService;


  /*  @Override
    @Transactional
    //TODO A voir si on maintient
    //@Transactional(readOnly = true)
    //TDODO A voir si on maintient
    public Person savePerson (Person person){
        logger.debug("savePerson start. Email : " + person.getEmail());
        Person resultPerson = null;
//TODO rajouter filtre sur password en retour
        //Check if email already exist
        if (personDao.findByEmail(person.getEmail())== null) {
            //Create person
            Person personToSave = new Person();
            personToSave = person;
            personToSave.setPassword(passwordEncoder.encode(person.getPassword()));
            resultPerson = personDao.save(personToSave);
*//*             Long psnID = personToSave.getId();

           psnID = Long.valueOf(5);

            logger.debug ("PSNID = "+ psnID.toString());*//*

            //Account acountPerson = new Account(psnID,0);
            //Account acountPerson = new Account(psnID,0,resultPerson);
            Account acountPerson = new Account(resultPerson,0);

            Account accountResult = accountDao.save (acountPerson);
            if (accountResult == null){
                resultPerson = null;
            }

        }

        return resultPerson;
    }*/


    public String signin(SignIn signIn) {
    //public String signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
            return jwtTokenProvider.createToken(signIn.getEmail(), personDao.findByEmail(signIn.getEmail()).getRoles());
        } catch (AuthenticationException e) {
            // throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
            logger.error("Invalid username/password supplied for " + signIn.getEmail());
            return "";
        }
    }

    public String signup(Person person) {
        if (!personDao.existsByEmail(person.getEmail())) {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT))); //TODO ICICICICICICICICICICICIU
            personDao.save(person);
            return jwtTokenProvider.createToken(person.getEmail(), person.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY); //TODO CUSOMISER L'ERREUR
        }
    }

    public MyBuddy addBuddy (MyBuddy myBuddy) {
        MyBuddy myBuddyResult = null;
        Person myPerson = null;
        Person buddyPerson = null;

        // Check if my email exist
        myPerson =  personDao.findByEmail(myBuddy.getMyEmail());
        if (myPerson != null) {
            //Check if  buddy email exist
            buddyPerson =  personDao.findByEmail(myBuddy.getBuddyEmail());
            if (buddyPerson != null) {
                //Check if he is already my buddy
                if (!utilService.checkBuddy(myBuddy.getMyEmail(),myBuddy.getBuddyEmail())) {
                    //Create buddy
                    myPerson.getBuddy().add(buddyPerson);
                    if (personDao.save(myPerson) != null) {
                        myBuddyResult = myBuddy;
                        myBuddyResult.setBuddyFirstname(buddyPerson.getFirstName());
                        myBuddyResult.setBuddyLastname(buddyPerson.getLastName());
                    }
                }

            }
        }
        return myBuddyResult;
    }

    /**
     * Find all person in database to seclect buddy
     * @return list of person
     */
    public List<Person> findAll (){
        return personDao.findAll();

    }
    public List<Person> findFriendByEmail(String email){
        Person myPerson = null;
        List<Person> buddys = null;
        // Check if my email exist
        myPerson =  personDao.findByEmail(email);
        if (myPerson != null) {
            buddys = myPerson.getBuddy();
        }
        return buddys;
    }
}

