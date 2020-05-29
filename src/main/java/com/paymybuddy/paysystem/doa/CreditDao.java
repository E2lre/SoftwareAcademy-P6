package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.Credit;
import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDao extends JpaRepository<Credit, Integer> {

}
