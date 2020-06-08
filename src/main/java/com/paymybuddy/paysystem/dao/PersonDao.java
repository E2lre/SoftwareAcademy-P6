package com.paymybuddy.paysystem.dao;

import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository <Person, Integer> {

    Person findByEmail(String email);
    boolean existsByEmail(String email);
    Person findByBuddy(Person person);
    boolean existsByEmailAndBuddy(String email, Person buddy);
}
