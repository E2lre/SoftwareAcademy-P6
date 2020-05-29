package com.paymybuddy.paysystem.doa;

import com.paymybuddy.paysystem.model.Payment;
import com.paymybuddy.paysystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {
}
