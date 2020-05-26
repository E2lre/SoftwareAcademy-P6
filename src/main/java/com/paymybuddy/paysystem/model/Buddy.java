package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="buddy")
public class Buddy implements Serializable {

    private static final Logger logger = LogManager.getLogger(Buddy.class);
/*    @Id
    private int personId;*/
    @Id
@   OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="person_id",foreignKey = @ForeignKey(name = "person_buddy_fk"))
    private Person person;
    /*@Id
    private int friendPersonId;*/
    @Id
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="friend_person_id",foreignKey = @ForeignKey(name = "person_buddy_fk1"))
    private Person friend;
    private Date creation;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buddy")
    private List<Payment> payments;

    public Buddy() {

    }

    public Buddy(Date creation) {
/*        this.personId = personId;
        this.friendPersonId = friendPersonId;*/
        this.creation = creation;
    }

 /*   public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getFriendPersonId() {
        return friendPersonId;
    }

    public void setFriendPersonId(int friendPersonId) {
        this.friendPersonId = friendPersonId;
    }*/

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }


}
