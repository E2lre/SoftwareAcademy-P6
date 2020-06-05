package com.paymybuddy.paysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.paysystem.config.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
//@JsonIgnoreProperties(value = {"password"})
@Table(name="person",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uniqueEmailConstraint")})
public class Person implements Serializable {


    @Id
    @GeneratedValue
    private long id;

    @Column(name="firstname",length=100)
    @NotNull
    private String firstName;

    @Column(name="lastname",length=100)
    @NotNull
    private String lastName;


    private Date birthdate;

    @Column(length=500,unique=true)
    @Email
    @NotNull
    private String email;

    @Column(length=200)
    @NotNull
    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Person> buddy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<BankInfo> bankinfos;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buddyCredit")
    private List<Credit> credits;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buddyPayment")
    private List<Payment> payments;

    public Person() {
    }

    public Person(long id, String firstName, String lastName, Date birthdate, String email,String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        if (birthdate == null) {
            this.birthdate = null;
        }
        else {
            this.birthdate = new Date(birthdate.getTime());
        }
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Person> getBuddy() {
        return buddy;
    }

    public void setBuddy(List<Person> buddy) {
        this.buddy = buddy;
    }
}
