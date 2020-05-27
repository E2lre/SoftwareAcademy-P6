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
    @JoinColumn(foreignKey = @ForeignKey(name = "bankinfo_person_fk"),name = "bankinfo_person_id")
    private Person person;
    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankinfo")
    private List<Transaction> transactions;*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankinfo")
    private List<BankTransfert> bankTransferts;

    public BankInfo() {

    }
    public BankInfo(int id, int type, String info, String description) {
        this.id = id;
        this.type = type;
        this.info = info;
        this.description = description;

    }
    public BankInfo(Person person, int type, String info, String description) {
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "BankInfo{" +
                "id=" + id +
                ", type=" + type +
                ", info='" + info + '\'' +
                ", description='" + description + '\'' +
                ", person=" + person +
                '}';
    }

/*    public int getAccountPersonId() {
        return accountPersonId;
    }

    public void setAccountPersonId(int accountPersonId) {
        this.accountPersonId = accountPersonId;
    }*/




}
