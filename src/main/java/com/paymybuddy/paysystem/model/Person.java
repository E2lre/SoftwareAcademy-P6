package com.paymybuddy.paysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.paysystem.config.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="person",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uniqueEmailConstraint")})
public class Person {

    private static final Logger logger = LogManager.getLogger(Person.class);

    @Id
    @GeneratedValue
    //@JsonView(View.User.class)
    private long id;

    @Column(name="firstname",length=100)
    //@JsonView(View.User.class)
    private String firstName;

    @Column(name="lastname",length=100)
    //@JsonView(View.User.class)
    private String lastName;

    //@JsonView(View.User.class)
    private Date birthdate;

    @Column(length=500,unique=true)
    @Email   //TODO : indiquer le nom de l'index corretement
    //@JsonView(View.User.class)
    private String email;

    @Column(length=200)
    //@JsonIgnore
    //@JsonView(View.Admin.class)
    private String password;


    @ManyToMany(fetch=FetchType.EAGER)
    private List<Person> buddy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    //@JsonView(View.User.class)
    private List<BankInfo> bankinfos;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

/*    @OneToMany(mappedBy = "person_id","friend_person_id")
    private List<Buddy> buddy;*/

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buddyCredit")
    private List<Credit> credits;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "buddyPayment")
    private List<Payment> payments;



 /*    @OneToMany(mappedBy = "friend_person_id")
    private List<Buddy> buddy;
*/

    public Person() {
    }

    public Person(long id, String firstName, String lastName, Date birthdate, String email,String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
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


}
