package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class BankTransaction {
    private static final Logger logger = LogManager.getLogger(BankInfo.class);

    @Id
    @GeneratedValue
    private int id;
    private int flow;
    private double amount;
    private String description;
    private int bankInfoId;
    private int accountPersonId;

    @OneToMany (mappedBy = "bankTransaction")
    private Collection<BankInfo> bankInfos;
    @OneToMany (mappedBy = "bankTransaction")
    private Collection<Account> acounts;

    public BankTransaction() {

    }
    public BankTransaction(int id, int flow, double amount, String description, int bankInfoId, int accountPersonId) {
        this.id = id;
        this.flow = flow;
        this.amount = amount;
        this.description = description;
        this.bankInfoId = bankInfoId;
        this.accountPersonId = accountPersonId;
    }


    public static Logger getLogger() {
        return logger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBankInfoId() {
        return bankInfoId;
    }

    public void setBankInfoId(int bankInfoId) {
        this.bankInfoId = bankInfoId;
    }

    public int getAccountPersonId() {
        return accountPersonId;
    }

    public void setAccountPersonId(int accountPersonId) {
        this.accountPersonId = accountPersonId;
    }



}
