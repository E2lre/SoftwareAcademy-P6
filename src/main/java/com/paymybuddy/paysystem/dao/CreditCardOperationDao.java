package com.paymybuddy.paysystem.dao;


import com.paymybuddy.paysystem.model.CreditCardOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardOperationDao extends JpaRepository<CreditCardOperation, Integer> {
}
