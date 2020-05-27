package com.paymybuddy.paysystem.service.person;

import com.paymybuddy.paysystem.doa.AccountDao;
import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Account;
import com.paymybuddy.paysystem.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    @Autowired
    private PersonDao personDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountDao accountDao;

    /**
     * Create a new user if the mail does not exist
     * Creation of person and account
     * @param person to be created
     * @return person created
     */
    @Override
    @Transactional
    //@Transactional(readOnly = true)
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
/*             Long psnID = personToSave.getId();

           psnID = Long.valueOf(5);

            logger.debug ("PSNID = "+ psnID.toString());*/

            //Account acountPerson = new Account(psnID,0);
            //Account acountPerson = new Account(psnID,0,resultPerson);
            Account acountPerson = new Account(resultPerson,0);

            Account accountResult = accountDao.save (acountPerson);
            if (accountResult == null){
                resultPerson = null;
            }

        }

        return resultPerson;
    }

    @Override
    public boolean checkPwdPerson (String pwd){
        Person resulpDBPerson = personDao.findById(3);
        boolean result = passwordEncoder.matches(pwd,resulpDBPerson.getPassword());
        if (result){
            logger.info("pwd ok");
            return true;
        } else
        {
            logger.info("pwd ko");
            return false;
        }
    }
}
