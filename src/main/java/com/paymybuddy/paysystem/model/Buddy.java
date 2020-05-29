/*
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
*/
/*    @Id
    private int personId;*//*

*/
/*    @Id
    @GeneratedValue
    @JoinColumn(name="buddyx_id")
    private long id;*//*


    @EmbeddedId
    private BuddyKey id;

    //@Id
    //@OneToOne(fetch= FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name="person_id",foreignKey = @ForeignKey(name = "person_buddy_fk"))
    @MapsId("person_id")
    private Person person;
    */
/*@Id
    private int friendPersonId;*//*

    //@Id
    //@OneToOne(fetch= FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name="friend_person_id",foreignKey = @ForeignKey(name = "person_buddy_fk1"))
    @MapsId("friend_person_id")
    private Person friend;
    */
/*private Date creation;*//*

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buddy")
    @MapsId
    private List<Payment> payments;

    public Buddy() {
        //super();
    }

*/
/*    public Buddy(Date creation) {
*//*
*/
/*        this.personId = personId;
        this.friendPersonId = friendPersonId;*//*
*/
/*
        this.creation = creation;
    }*//*



*/
/*
    public Buddy(long id,Date creation) {
        this.id = id;
        this.creation = creation;
    }
*//*


    public Buddy(Person person, Person friend) {
        this.person = person;
        this.friend = friend;

    }
    public Buddy(Person person, Person friend, List<Payment> payments) {
        this.person = person;
        this.friend = friend;
        this.payments = payments;
    }
*/
/*    public Buddy(Person person, Person friend, Date creation) {
        this.person = person;
        this.friend = friend;
        this.creation = creation;
    }
    public Buddy(Person person, Person friend, Date creation, List<Payment> payments) {
        this.person = person;
        this.friend = friend;
        this.creation = creation;
        this.payments = payments;
    }*//*

*/
/*    public Buddy(long id, Person person, Person friend, Date creation, List<Payment> payments) {
        this.id = id;
        this.person = person;
        this.friend = friend;
        this.creation = creation;
        this.payments = payments;
    }*//*


 */
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
    }*//*


*/
/*
    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }
*//*



}
*/
