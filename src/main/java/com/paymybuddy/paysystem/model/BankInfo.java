package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="bankinfo")
public class BankInfo implements Serializable {
    private static final Logger logger = LogManager.getLogger(BankInfo.class);

    @Id
    @GeneratedValue
    private int id;
    private int type;
    private String info;
    private String description;
    //private int accountPersonId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "account_bankinfo_fk"),name = "account_person_id")
    private Account account;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankinfo")
    private List<Transaction> transactions;

    public BankInfo() {

    }
    public BankInfo(int id, int type, String info, String description) {
        this.id = id;
        this.type = type;
        this.info = info;
        this.description = description;

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/*    public int getAccountPersonId() {
        return accountPersonId;
    }

    public void setAccountPersonId(int accountPersonId) {
        this.accountPersonId = accountPersonId;
    }*/




}
