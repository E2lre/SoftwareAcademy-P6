package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class BankInfo {
    private static final Logger logger = LogManager.getLogger(BankInfo.class);

    @Id
    @GeneratedValue
    private int id;
    private int type;
    private String info;
    private String description;
    private int personId;

    @OneToMany (mappedBy = "bankInfo")
    private Collection<Person> persons;
    @ManyToOne
    private BankTransaction bankTransaction;

    public BankInfo() {

    }
    public BankInfo(int id, int type, String info, String description, int personId) {
        this.id = id;
        this.type = type;
        this.info = info;
        this.description = description;
        this.personId = personId;
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }




}
