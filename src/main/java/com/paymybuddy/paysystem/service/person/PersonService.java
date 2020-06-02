package com.paymybuddy.paysystem.service.person;

import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.questions.MyBuddy;
import com.paymybuddy.paysystem.model.questions.SignIn;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PersonService {
    /*public Person savePerson (Person person);*/
    public String signin(SignIn signIn);
    public String signup(Person person);
    public MyBuddy addBuddy (MyBuddy myBuddy);
    public List<Person> findAll ();
    public List<Person> findFriendByEmail(String email);
}
