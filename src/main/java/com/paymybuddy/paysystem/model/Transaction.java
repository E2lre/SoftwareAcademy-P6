package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="transaction")
public class Transaction implements Serializable {
    private static final Logger logger = LogManager.getLogger(Transaction.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    //@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "account_transaction_fk"),name = "account_person_id")
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "bankinfo_transaction_fk"),name = "bankinfo_id")
    private BankInfo bankinfo;
    //private int accountPersonId;
    //@Id
    //private int bankInfoId;
    private double amount;
    private String description;
    private Date transactionDate;

/*    @OneToOne(mappedBy = "transac")
    BuddyTransaction buddytransaction;*/
/*    @OneToOne(mappedBy = "transac")
    BankTransaction banktransaction;*/


 /*   @OneToOne(fetch = FetchType.LAZY, mappedBy = "transaction")
    private BuddyTransaction buddyTransaction;*/
   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "transactionId", referencedColumnName = "id")*/
/*    @OneToOne(mappedBy = "transaction")
    private BuddyTransaction buddyTransaction;*/

/*    @OneToMany (mappedBy = "transaction")
    private Collection<Person> persons;
    @OneToMany (mappedBy = "transaction")
    private Collection<Person> buddy;*/


    public Transaction() {

    }
    public Transaction(Long id, double amount, String description, Date transactionDate) {
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

 /*   public int getAccountPersonId() {
        return accountPersonId;
    }

    public void setAccountPersonId(int accountPersonId) {
        this.accountPersonId = accountPersonId;
    }*/

    /*public int getBankInfoId() {
        return bankInfoId;
    }

    public void setBankInfoId(int bankInfoId) {
        this.bankInfoId = bankInfoId;
    }*/



}
