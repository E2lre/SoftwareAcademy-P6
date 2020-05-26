package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.Account;

import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {


}
