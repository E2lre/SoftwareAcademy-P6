package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.BankTransaction2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransaction2Dao extends JpaRepository<BankTransaction2, Integer> {

}
