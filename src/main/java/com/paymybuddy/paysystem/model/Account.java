package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

    private static final Logger logger = LogManager.getLogger(Person.class);

    @Id
    private int personId;
    private double balance;
}
