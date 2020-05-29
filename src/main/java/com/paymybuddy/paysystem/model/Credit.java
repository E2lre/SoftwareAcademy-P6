package com.paymybuddy.paysystem.model;

import javax.persistence.*;

@Entity
@Table(name="credit")
@DiscriminatorValue("Credit")
public class Credit extends Transaction {


 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
            foreignKey = @ForeignKey(name = "buddy_credit_fk2"), value =
            {
                    @JoinColumn( name = "buddy_person_id", referencedColumnName="person_id"),
                    @JoinColumn( name = "buddy_friend_person_id", referencedColumnName="friend_person_id")
            }
    )
    private Buddy buddy;
*/




    /*   @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumns(
                foreignKey = @ForeignKey(name = "buddy_credit_fk2"), value =
                {
                        @JoinColumn( name = "buddy_friend_person_id", referencedColumnName="friend_person_id")
                }
        )*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "credit_person_fk"),name = "credit_person_id")
    private Person buddyCredit;


    public Credit() {
    }

    public Person getBuddyCredit() {
        return buddyCredit;
    }

    public void setBuddyCredit(Person buddyCredit) {
        this.buddyCredit = buddyCredit;
    }


}
