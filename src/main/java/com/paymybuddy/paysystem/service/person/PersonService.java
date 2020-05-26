package com.paymybuddy.paysystem.service.person;

import com.paymybuddy.paysystem.model.Person;

import java.util.List;

public interface PersonService {
    public Person savePerson (Person person);
    public boolean checkPwdPerson (String pwd);

}
