package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;
@Entity
public class Transaction {
    private static final Logger logger = LogManager.getLogger(Transaction.class);

    @Id
    @GeneratedValue
    private int id;
    private double amount;
    private double commission;
    private String description;
    private Date transactionDate;
    private int userPersonId;
    private int buddyPersonId;

    @OneToMany (mappedBy = "transaction")
    private Collection<Person> persons;
    @OneToMany (mappedBy = "transaction")
    private Collection<Person> buddy;


    public Transaction() {

    }
    public Transaction(int id, double amount, double commission, String description, Date transactionDate, int userPersonId, int buddyPersonId) {
        this.id = id;
        this.amount = amount;
        this.commission = commission;
        this.description = description;
        this.transactionDate = transactionDate;
        this.userPersonId = userPersonId;
        this.buddyPersonId = buddyPersonId;

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

    public double getAccount() {
        return amount;
    }

    public void setAccount(double amount) {
        this.amount = amount;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getUserPersonId() {
        return userPersonId;
    }

    public void setUserPersonId(int userPersonId) {
        this.userPersonId = userPersonId;
    }

    public int getBuddyPersonId() {
        return buddyPersonId;
    }

    public void setBuddyPersonId(int buddyPersonId) {
        this.buddyPersonId = buddyPersonId;
    }



}
