package com.paymybuddy.paysystem.dao;

import com.paymybuddy.paysystem.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDao extends JpaRepository<Credit, Integer> {

}
