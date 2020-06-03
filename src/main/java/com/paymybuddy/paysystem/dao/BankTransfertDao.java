package com.paymybuddy.paysystem.dao;


import com.paymybuddy.paysystem.model.BankTransfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransfertDao extends JpaRepository<BankTransfert, Integer> {

}
