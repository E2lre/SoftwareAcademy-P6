package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository <Person, Integer> {
    Person findById(long id); //TODO a voir s'il faut laisser par la suite
    Person findByLastName(String name); //TODO a voir s'il faut laisser par la suite
    Person findByEmail(String email); //TODO commenter correctement
}
