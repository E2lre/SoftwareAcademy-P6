package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="transaction2")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="transaction_type")
public abstract class Transaction2 implements Serializable {
    private static final Logger logger = LogManager.getLogger(Transaction.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    //@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "account_transaction_fk2"),name = "account_person_id")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "bankinfo_transaction_fk2"),name = "bankinfo_id")
    private BankInfo bankinfo;
    //private int accountPersonId;
    //@Id
    //private int bankInfoId;
    private double amount;
    private String description;
    private Date transactionDate;

    public Transaction2() {

    }
    public Transaction2(Long id, double amount, String description, Date transactionDate) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        //this.accountPersonId = accountPersonId;
        //this.bankInfoId = bankInfoId;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAccount() {
        return amount;
    }

    public void setAccount(double amount) {
        this.amount = amount;
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
}
