package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="account")
public class Account implements Serializable {

    private static final Logger logger = LogManager.getLogger(Account.class);

    @Id
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="person_id",foreignKey = @ForeignKey(name = "person_account_fk"))
    private Person person;
    private double balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<BankInfo> bankinfos;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions;


    public Account() {

    }
    public Account( double balance) {

        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
