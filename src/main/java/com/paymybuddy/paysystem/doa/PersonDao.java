package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository <Person, Integer> {
    Person findById(int id);
    Person findByLastName(String name);
}
