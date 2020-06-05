package com.paymybuddy.paysystem.service.person;

import com.paymybuddy.paysystem.config.JwtTokenProvider;
import com.paymybuddy.paysystem.dao.AccountDao;
import com.paymybuddy.paysystem.dao.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.Role;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
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
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * Signin a user
     * @param person only email en password are povided
     * @return JWT tocken, blank if error
     */
    public String signin(Person person) {
       logger.info("start");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(person.getEmail(), person.getPassword()));
            logger.info("finish");
            return jwtTokenProvider.createToken(person.getEmail(), personDao.findByEmail(person.getEmail()).getRoles());
        } catch (AuthenticationException e) {

            logger.error("Invalid username/password supplied for " + person.getEmail());
            return "";
        }
    }

    /**
     * Signup a new user
     * @param person Personal informations
     * @return JWT token, blank if error
     */
    @Transactional
    public String signup(Person person) {
        boolean result = false;
        logger.info("start");
        if (!personDao.existsByEmail(person.getEmail())) {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
            Person resultPerson = personDao.save(person);
            if (resultPerson != null) {
                Account acountPerson = new Account(person, 0);

                Account accountResult = accountDao.save(acountPerson);
                if (accountResult != null) {
                    result = true;

                }
            }
        }
        if (result) {
            logger.info("start");
            return jwtTokenProvider.createToken(person.getEmail(), person.getRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Add a freind to a person
     * @param myBuddy Information to create a buddy
     * @return Information about the buddy, null if error
     */
    public MyBuddy addBuddy (MyBuddy myBuddy) {
        logger.info("start");
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
        logger.info("finish");
        return myBuddyResult;
    }

    /**
     * Find all person in database to seclect buddy
     * @return list of person
     */
    public List<Person> findAll (){
        logger.info("start/finish");
        return personDao.findAll();
    }

    /**
     * Fin the list of friend for a person
     * @param email Email of the person to fin his friends
     * @return list of friend. return null if eoor
     */
    public List<Person> findFriendByEmail(String email){
        logger.info("start");
        Person myPerson = null;
        List<Person> buddys = null;
        // Check if my email exist
        myPerson =  personDao.findByEmail(email);
        if (myPerson != null) {
            buddys = myPerson.getBuddy();
        } else{
            logger.error("Nobody found for email " + email);
        }
        logger.info("finish");
        return buddys;
    }
}

