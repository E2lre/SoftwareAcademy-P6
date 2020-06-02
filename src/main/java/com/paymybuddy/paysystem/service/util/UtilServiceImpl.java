package com.paymybuddy.paysystem.service.util;

import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.service.person.PersonServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilServiceImpl implements UtilService {
    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    @Autowired
    private PersonDao personDao;

    /**
     * check if a person as a buddy
     * @param myEmail email of the user
     * @param buddyEmail email of the buddy to check
     * @return true if they are buddy, else false
     */
    public boolean checkBuddy (String myEmail, String buddyEmail) {

        Person myPerson = null;
        Person buddyPerson = null;
        boolean result = false;
        //Get my person
        myPerson =  personDao.findByEmail(myEmail);

        if (myPerson != null) {
            //Get buddy Person
            buddyPerson = personDao.findByEmail(buddyEmail);
            if (buddyPerson != null) {
                //Are they friend
                //solution with JPA
                result = personDao.existsByEmailAndBuddy(myPerson.getEmail(),buddyPerson);

  // Solution sans JPA
  /*              int index;
                for (index = 0; index < myPerson.getBuddy().size(); index++) {
                    if (myPerson.getBuddy().get(index).getId() == buddyPerson.getId()) {
                        result = true;
                    }
                }*/
            }
        }
        return result;
    }
}
