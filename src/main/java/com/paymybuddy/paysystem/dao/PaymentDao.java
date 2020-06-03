package com.paymybuddy.paysystem.dao;

import com.paymybuddy.paysystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {
}
