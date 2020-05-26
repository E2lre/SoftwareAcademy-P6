package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="person",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uniqueEmailConstraint")})
public class Person {

    private static final Logger logger = LogManager.getLogger(Person.class);

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="firstname",length=100)
    private String firstName;
    @Column(name="lastname",length=100)
    private String lastName;
    private Date birthdate;
    @Column(length=500,unique=true)
    @Email   //TODO : indiquer le nom de l'index corretement
    private String email;
    @Column(length=200)
    private String password;



    public Person() {
    }

    public Person(Long id, String firstName, String lastName, Date birthdate, String email,String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        Date localDate = birthdate;
        return localDate;
    }

    public void setBirthdate(Date birthdate) {
        if (birthdate == null) {
            this.birthdate = null;
        }
        else {
            this.birthdate = new Date(birthdate.getTime());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
