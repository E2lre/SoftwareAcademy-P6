package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.Person;
import com.paymybuddy.paysystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
}
