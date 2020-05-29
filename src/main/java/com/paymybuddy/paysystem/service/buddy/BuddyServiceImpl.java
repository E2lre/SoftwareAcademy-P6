/*
package com.paymybuddy.paysystem.service.buddy;

import com.paymybuddy.paysystem.doa.BuddyDao;
import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.service.person.PersonServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BuddyServiceImpl implements BuddyService {
    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    private BuddyDao buddyDao;
    @Autowired
    private PersonDao personDao;

    */
/**
     * add a buddy
     * @param myBuddy information whith me ans my buddy
     * @return my buddy completed
     *//*

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
                //Check if he is allready my buddy

                //Create buddy
                //Date now = new Date(now.getTime());
                //Date now = new Date();
                //Buddy buddy = new Buddy(myPerson,buddyPerson,now);
                //Buddy buddy = new Buddy(myPerson,buddyPerson);

*/
/*                if (buddyDao.save(buddy)!= null) {
                    myBuddyResult = myBuddy;
                    myBuddyResult.setBuddyFirstname(buddyPerson.getFirstName());
                    myBuddyResult.setBuddyLastname(buddyPerson.getLastName());
                }*//*

            }
        }
        return myBuddyResult;
    }
}
*/
