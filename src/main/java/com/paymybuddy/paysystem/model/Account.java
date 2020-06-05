package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="account")
public class Account implements Serializable {

    private static final Logger logger = LogManager.getLogger(Account.class);



    @Id
    private long id;
    @Column(columnDefinition = "Decimal(6,2)")
    private double balance;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="person_id",foreignKey = @ForeignKey(name = "person_account_fk"))
    @MapsId
    private Person person;


/*    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<BankInfo> bankinfos;*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions;


    public Account() {

    }
    public Account( long id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account( long id ,double balance, Person person) {
        this.id = id;
        this.balance = balance;
        this.person = person;
    }
    public Account( Person person, double balance) {
        this.person = person;
        this.balance = balance;
    }
    public Account( Person person, double balance,List<Transaction> transactions) {
        this.person = person;
        this.balance = balance;
        this.transactions = transactions;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
 /*   public static class AccountId implements Serializable {
        private Person person;

        public AccountId() {

        }

        public AccountId(Person person) {
            this.person = person;
        }

        @Override
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }
            if (!(o instanceof Account)) {
                return false;
            }
            Account account = (Account) o;
            return Objects.equals(person, account.getPerson());
        }

        @Override
        public int hashCode() {
            return Objects.hash(person);
        }
    }*/

 /*       @Override
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }
            if (!(o instanceof Account)) {
                return false;
            }
            Account account = (Account) o;
            return Objects.equals(person, account.getPerson()) &&
                    Objects.equals(balance, account.getBalance()) &&
                    Objects.equals(bankinfos, account.getBankinfos()) &&
                    Objects.equals(transactions, account.getTransactions());
        }

        @Override
        public int hashCode() {
            return Objects.hash(person, balance, bankinfos, transactions);
        }*/

}
