/*
package com.paymybuddy.paysystem.web.controller;

import com.paymybuddy.paysystem.doa.PersonDao;
import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.service.buddy.BuddyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuddyController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private BuddyService buddyService;
    */
/*---------------------------  Post addBuddy -----------------------------*//*

    //@JsonView(View.User.class)

    */
/**
     *
     * @param myBuddy
     * @return
     *//*

    @PostMapping(value="/addBuddy")
    public MyBuddy signup(@RequestBody MyBuddy myBuddy) {
        // public Person addPerson(@Valid @RequestBody Person person) {

        //logger.info("POST/person=" + person);

        //Person personResult = personDao.save(person);

        //logger.info("POST /person : " + personResult);

        //return personResult;
        logger.info("addBuddy");
        return buddyService.addBuddy(myBuddy);
    }
}
*/
