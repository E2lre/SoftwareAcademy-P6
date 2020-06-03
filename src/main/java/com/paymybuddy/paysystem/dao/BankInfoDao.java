package com.paymybuddy.paysystem.dao;

import com.paymybuddy.paysystem.model.BankInfo;
import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankInfoDao extends JpaRepository<BankInfo, Integer> {
    List<BankInfo> findByPerson(Person person);//TODO commenter correctement
}
