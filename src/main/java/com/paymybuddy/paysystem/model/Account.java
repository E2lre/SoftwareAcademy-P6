package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
public class Account {

    private static final Logger logger = LogManager.getLogger(Account.class);

    @Id
    private int personId;
    private double balance;

    @ManyToOne
    private BankTransaction bankTransaction;


    public Account() {

    }
    public Account(int personId, double balance) {
        this.personId = personId;
        this.balance = balance;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
