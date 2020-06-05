package com.paymybuddy.paysystem.model.questions;

import com.paymybuddy.paysystem.model.*;

import java.util.Date;
import java.util.List;

/**
 * This class is use for the response with PErson class, but without password
 */
public class PersonDTO {


    private long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String email;
    private List<Person> buddy;

    public PersonDTO() {
    }

    public PersonDTO(long id, String firstName, String lastName, Date birthdate, String email, List<Person> buddy) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.buddy = buddy;
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


    public List<Person> getBuddy() {
        return buddy;
    }

    public void setBuddy(List<Person> buddy) {
        this.buddy = buddy;
    }






}
