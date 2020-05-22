package com.paymybuddy.paysystem.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="buddytransaction")
public class BuddyTransaction implements Serializable {

    private static final Logger logger = LogManager.getLogger(Buddy.class);

    @Id
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="transaction_id",foreignKey = @ForeignKey(name = "transaction_buddytransaction_fk"))
    private Transaction transaction;

    private double commissionAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "buddy_buddytransaction_fk"), value =
            {
            @JoinColumn( name = "buddy_person_id", referencedColumnName="person_id"),
            @JoinColumn( name = "buddy_friend_person_id", referencedColumnName="friend_person_id")
            }
    )
/*    @JoinColumns({
            @JoinColumn(foreignKey = @ForeignKey(name = "buddy_buddytransaction_fk"), name = "buddy_person_id", referencedColumnName="person_id"),
            @JoinColumn(foreignKey = @ForeignKey(name = "buddy_buddytransaction_fk"), name = "buddy_friend_person_id", referencedColumnName="friend_person_id")
    })*/
    private Buddy buddy;
    /*private int buddyPersonId ;
    private int buddyFriendPersonId;*/

    //@JoinColumn (name = "id", referencedColumnName = "transactionId")
    /*@OneToOne
    @JoinColumn (name = "transaction_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "atransaction_buddytransaction_fk"))*/

 /*   @OneToOne(mappedBy = "buddyTransaction")*/
    //@OneToOne(cascade = CascadeType.ALL)
 /*   @OneToOne
    //@JoinColumn (name = "id", referencedColumnName = "transactionId")
    @JoinColumn (name = "transactionId", referencedColumnName = "id")
    private Transaction transaction;*/

    //@OneToOne(mappedBy = "buddytransaction")
    //@JoinColumn(name = "person_id")
    //@JoinColumn(foreignKey = @ForeignKey(name = "transaction_buddytransaction_fk"),name = "id")
   // private Transaction transaction;

    public BuddyTransaction() {

    }
    public BuddyTransaction( double commissionAmount) {
        this.commissionAmount = commissionAmount;
/*        this.buddyPersonId = buddyPersonId;
        this.buddyFriendPersonId = buddyFriendPersonId;*/
    }


    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

  /*  public int getBuddyPersonId() {
        return buddyPersonId;
    }

    public void setBuddyPersonId(int buddyPersonId) {
        this.buddyPersonId = buddyPersonId;
    }

    public int getBuddyFriendPersonId() {
        return buddyFriendPersonId;
    }

    public void setBuddyFriendPersonId(int buddyFriendPersonId) {
        this.buddyFriendPersonId = buddyFriendPersonId;
    }
*/




}
